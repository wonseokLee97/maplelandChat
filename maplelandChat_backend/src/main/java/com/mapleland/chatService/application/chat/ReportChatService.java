package com.mapleland.chatService.application.chat;

import com.mapleland.chatService.presentation.chat.dto.ReportChatRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportChatService {
    private final ChatDomainService chatDomainService;

    public void execute(ReportChatRequestDto requestDto) {
        chatDomainService.reportChat(requestDto);
    }
}
