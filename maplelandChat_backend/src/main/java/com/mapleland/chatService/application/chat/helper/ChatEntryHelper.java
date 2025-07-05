package com.mapleland.chatService.application.chat.helper;

import com.mapleland.chatService.application.redis.RedisService;
import com.mapleland.chatService.presentation.websocket.dto.EntryRequestDto;
import com.mapleland.chatService.presentation.websocket.dto.EntryResponseDto;
import com.mapleland.chatService.shared.utils.HashUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
@Slf4j
public class ChatEntryHelper {

    private final RedisService redisService;

    public EntryResponseDto increase(EntryRequestDto entryRequestDto, String uuid) {
        uuid = HashUtil.sha256(uuid);
        String itemId = entryRequestDto.getItemId();

        String itemIdKey = "chat:entry:" + itemId; // 채팅 정보
        String entryUuid = "chat:uuid:" + uuid; // 접속자 정보

        // 문제는 동시에 증가시키면 Redis 가 레이스 컨디션을 잡아주나?
        long count = redisService.increment(itemIdKey);
//        redisService.set(itemIdKey, String.valueOf(count), null);

        return EntryResponseDto.builder()
                .entryCnt(count)
                .build();
    }

    public EntryResponseDto decrease(String itemId, String uuid) {
        uuid = HashUtil.sha256(uuid);
        String entryKey  = "chat:entry:" + itemId; // 채팅 정보
        String dedupKey = "chat:entry:dedup:" + itemId + ":" + uuid; // 중복 decrease 방지

        Boolean alreadyProcessed = redisService.exists(dedupKey);
        if (Boolean.TRUE.equals(alreadyProcessed)) {
            String currentValue = redisService.get(entryKey);
            long entryCnt = currentValue == null ? 0 : Long.parseLong(currentValue);

            return EntryResponseDto.builder()
                    .entryCnt(entryCnt) // 현재 값 유지
                    .build();
        }

        // 중복 아님 → 처리
        redisService.set(dedupKey, "1", Duration.ofSeconds(1));

        long entryCnt = redisService.decreaseAndDeleteIfZero(entryKey);
        return EntryResponseDto.builder()
                .entryCnt(entryCnt)
                .build();
    }
}
