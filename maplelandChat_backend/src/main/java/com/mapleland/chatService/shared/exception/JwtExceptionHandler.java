package com.mapleland.chatService.shared.exception;

import com.mapleland.chatService.shared.api.ApiResponse;
import com.mapleland.chatService.shared.jwt.JwtException;
import com.mapleland.chatService.shared.type.http.HttpErrorType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * SecurityFilter 내에서 발생한 오류만 Catch
 */

@RestControllerAdvice
public class JwtExceptionHandler {

    @ExceptionHandler(JwtException.ExpiredJwtException.class)
    protected ApiResponse<?> handleExpiredJwtException(JwtException.ExpiredJwtException e) {
        return ApiResponse
                .error(
                        e.getMessage(),
                        HttpErrorType.EXPIRED_TOKEN
                );
    }
}
