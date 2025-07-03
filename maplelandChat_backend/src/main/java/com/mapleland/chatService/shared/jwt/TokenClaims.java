package com.mapleland.chatService.shared.jwt;

import lombok.Builder;
import lombok.Getter;

@Builder
public record TokenClaims(
        @Getter
        String id,
        String issuer,
        @Getter
        String userName,
        @Getter
        String imgPath,
        String audience,
        long issuedAt,
        long notBefore,
        long expiresAt
) { }