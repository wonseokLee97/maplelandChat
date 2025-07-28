package com.mapleland.chatservice.application.chat.service;

import com.mapleland.chatservice.presentation.chat.dto.ReportChatRequestDto;
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
