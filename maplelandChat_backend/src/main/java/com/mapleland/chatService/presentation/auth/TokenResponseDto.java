package com.mapleland.chatService.presentation.auth;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TokenResponseDto {
    String accessToken;
}
