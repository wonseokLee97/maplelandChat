package com.mapleland.chatservice.presentation.exception;

import com.mapleland.chatservice.domain.chat.exception.ChatException;
import com.mapleland.chatservice.shared.api.ApiResponse;
import com.mapleland.chatservice.shared.type.http.HttpErrorType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * ChatController Layer 에서 발생한 예외만 Catch
 */
@RestControllerAdvice(basePackages = "com.mapleland.chatservice.presentation.chat")
public class ChatExceptionHandler {

    @ExceptionHandler(ChatException.AlreadyReportedUserException.class)
    protected ResponseEntity<?> handleAlreadyReportedUserException(ChatException.AlreadyReportedUserException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error(
                e.getMessage(),
                HttpErrorType.ALREADY_REPORTED
        ));
    }

    @ExceptionHandler(ChatException.AlreadyBannedUserException.class)
    protected ResponseEntity<?> handleAlreadyBannedUserException(ChatException.AlreadyBannedUserException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error(
                e.getMessage(),
                HttpErrorType.ALREADY_BANNED
        ));
    }
}
