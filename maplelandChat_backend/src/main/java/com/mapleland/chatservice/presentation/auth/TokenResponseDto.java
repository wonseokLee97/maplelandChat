package com.mapleland.chatservice.presentation.auth;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TokenResponseDto {
    String accessToken;
}
