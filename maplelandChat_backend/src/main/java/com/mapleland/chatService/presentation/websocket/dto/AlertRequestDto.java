package com.mapleland.chatService.presentation.websocket.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AlertRequestDto {
    private String message;
    private String sender;
    private String targetUuid;
    private String itemName;
}