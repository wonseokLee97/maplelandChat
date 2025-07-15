package com.mapleland.chatservice.shared.jwt;

public class JwtException extends RuntimeException {
    public JwtException(String message) {
        super(message);
    }

    public JwtException(String message, Throwable cause) {
        super(message, cause);
    }

    public static class TokenException extends JwtException {
        public TokenException(String message) {
            super(message);
        }

        public TokenException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class ExpiredJwtException extends JwtException {
        public ExpiredJwtException(String message) {
            super(message);
        }

        public ExpiredJwtException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class IllegalArgumentException extends JwtException {
        public IllegalArgumentException(String message) {
            super(message);
        }

        public IllegalArgumentException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class MalformedJwtException extends JwtException {
        public MalformedJwtException(String message) {
            super(message);
        }

        public MalformedJwtException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
