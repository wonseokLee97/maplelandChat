package com.mapleland.chatservice.shared.exception;

import com.mapleland.chatservice.shared.api.ApiResponse;
import com.mapleland.chatservice.shared.jwt.JwtException;
import com.mapleland.chatservice.shared.type.http.HttpErrorType;
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
