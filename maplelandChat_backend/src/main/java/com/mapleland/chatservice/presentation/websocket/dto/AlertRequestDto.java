package com.mapleland.chatservice.presentation.websocket.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AlertRequestDto {
    private String message;
    private String sender;
    private String targetUuid;
    private String itemName;
    private String itemId;
}