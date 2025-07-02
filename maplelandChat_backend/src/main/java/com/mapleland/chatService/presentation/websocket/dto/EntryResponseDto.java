package com.mapleland.chatService.presentation.websocket.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EntryResponseDto {
    private long entryCnt;
}
