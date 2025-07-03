package com.mapleland.chatService.domain.auth.exception;

import com.mapleland.chatService.domain.chat.exception.ChatException;

public class AuthException extends RuntimeException {
    public AuthException(String message) {
        super(message);
    }

    public AuthException(String message, Throwable cause) {
        super(message, cause);
    }

    public static class UnknownHostException extends ChatException {
        public UnknownHostException(String message) {
            super(message);
        }

        public UnknownHostException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class RenameRateLimitException extends ChatException {
        public RenameRateLimitException(String message) {
            super(message);
        }

        public RenameRateLimitException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
