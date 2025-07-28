package com.mapleland.chatservice.presentation.chat.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReportChatRequestDto {
    private String chatId;
    private String reporterUuid;
    private String reportedUuid;
}
