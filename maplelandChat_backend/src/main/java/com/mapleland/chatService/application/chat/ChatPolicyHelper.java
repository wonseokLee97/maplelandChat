package com.mapleland.chatService.application.chat;

import com.mapleland.chatService.application.redis.RedisService;
import com.mapleland.chatService.domain.chat.exception.ChatException;
import com.mapleland.chatService.presentation.websocket.dto.ChatRequestDto;
import com.mapleland.chatService.presentation.chat.dto.ReportChatRequestDto;
import com.mapleland.chatService.shared.utils.HashUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class ChatPolicyHelper {

    private final RedisService redisService;

    public void checkSpamAndBlock(String uuid) {
        uuid = HashUtil.sha256(uuid);
        checkIsBlocked(uuid);

        String chatCountKey = "chat:count:" + uuid;
        String chatBlockKey = "chat:block:" + uuid;

        long count = redisService.increment(chatCountKey);

        if (count == 1) {
            redisService.expire(chatCountKey, Duration.ofSeconds(5));
        } else if (count >= 5) {
            redisService.set(chatBlockKey, "1", Duration.ofSeconds(5));
            throw new ChatException.ChatBlockedException("너무 많은 대화는 다른 사용자에게 피해가 될 수 있습니다. \n채팅 기능이 5초간 제한됩니다.");
        }
    }

    public void checkRepeatedMessage(ChatRequestDto chatRequestDto, String uuid) {
        uuid = HashUtil.sha256(uuid);
        checkIsBlocked(uuid);

        String chatCountKey = "message:count:" + uuid + "::" + chatRequestDto.getMessage();
        String chatBlockKey = "chat:block:" + uuid;

        long count = redisService.increment(chatCountKey);

        if (count == 1) {
            redisService.expire(chatCountKey, Duration.ofSeconds(5));
        } else if (count >= 3) {
            redisService.set(chatBlockKey, "1", Duration.ofSeconds(5));
            throw new ChatException.ChatBlockedException("같은 말을 반복하는 것은 다른 사용자에게 피해가 될 수 있습니다. \n채팅 기능이 5초간 제한됩니다.");
        }
    }

    public void checkReportAndBan(ReportChatRequestDto requestDto) {
        String reporterUuid = HashUtil.sha256(requestDto.getReporterUuid());
        String reportedUuid = HashUtil.sha256(requestDto.getReportedUuid());

        String reportKey = "chat:report:" + reportedUuid;
        String repeatedReportKey = "chat:report:" + reporterUuid + "::" + reportedUuid;
        String bannedUserKey = "chat:block:bannedUser:" + reportedUuid;

        if ("1".equals(redisService.get(bannedUserKey))) {
            throw new ChatException.AlreadyBannedUserException("이미 제재당한 유저입니다.");
        }

        if ("1".equals(redisService.get(repeatedReportKey))) {
            throw new ChatException.AlreadyReportedUserException("이미 신고한 사용자입니다.");
        }

        long reportCount = redisService.increment(reportKey);
        redisService.set(repeatedReportKey, "1", Duration.ofHours(24));

        if (reportCount == 1) {
            redisService.expire(reportKey, Duration.ofHours(24));
        } else if (reportCount >= 10) {
            redisService.set(bannedUserKey, "1", Duration.ofMinutes(30));
        }
    }

    public void checkIsBlocked(String uuid) {
        String chatBlockKey = "chat:block:" + uuid;
        if ("1".equals(redisService.get(chatBlockKey))) {
            long sec = redisService.getTTLSec(chatBlockKey);
            throw new ChatException.ChatBlockedException("도배 감지: " + sec + "초 이후에 채팅을 시도하세요.");
        }
    }

    public void checkIsBanned(String uuid) {
        String bannedUserKey = "chat:block:bannedUser:" + uuid;
        if ("1".equals(redisService.get(bannedUserKey))) {
            long min = redisService.getTTLMin(bannedUserKey);
            throw new ChatException.ChatBlockedException("- 차단: " + min + "분 이후에 채팅을 시도하세요.\n- 사유: 신고 누적");
        }
    }
}
