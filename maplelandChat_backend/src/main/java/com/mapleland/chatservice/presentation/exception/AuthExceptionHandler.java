package com.mapleland.chatservice.presentation.exception;

import com.mapleland.chatservice.domain.auth.exception.AuthException;
import com.mapleland.chatservice.shared.api.ApiResponse;
import com.mapleland.chatservice.shared.type.http.HttpErrorType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * AuthController Layer 에서 발생한 예외만 Catch
 */
@RestControllerAdvice(basePackages = "com.mapleland.chatService.presentation.auth")
public class AuthExceptionHandler {

    @ExceptionHandler(AuthException.UnknownHostException.class)
    protected ResponseEntity<?> handleUnknownHostException(AuthException.UnknownHostException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error(
                e.getMessage(),
                HttpErrorType.INTERNAL_SERVER_ERROR
        ));
    }

    @ExceptionHandler(AuthException.RenameRateLimitException.class)
    protected ResponseEntity<?> handleRenameRateLimitException(AuthException.RenameRateLimitException e) {
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(ApiResponse.error(
                e.getMessage(),
                HttpErrorType.TOO_MANY_REQUESTS
        ));
    }
}
