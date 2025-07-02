package com.mapleland.chatService.presentation.exception;

import com.mapleland.chatService.domain.chat.exception.ChatException;
import com.mapleland.chatService.shared.api.ApiResponse;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.List;

import static com.mapleland.chatService.shared.type.ws.WsErrorType.*;

/**
 * 웹 소켓 통신 Layer 에서 발생한 예외만 Catch
 */
@ControllerAdvice
public class WebSocketExceptionHandler {

    @MessageExceptionHandler(MethodArgumentNotValidException.class)
    @SendToUser(destinations = "/queue/errors", broadcast = false)  // 해당 Session 의 User 에게만 예외 메시지 전송
    protected ApiResponse<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        assert e.getBindingResult() != null;
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        for (FieldError err : fieldErrors) {
            String code = err.getCode();

            switch (code) {
                case "Size":
                    return ApiResponse.error(INVALID_MESSAGE_SIZE);
                case "NotBlank":
                    return ApiResponse.error(INVALID_MESSAGE_NOT_BLANK);
            }
        }

        return null;
    }

    @MessageExceptionHandler(ChatException.ChatBlockedException.class)
    @SendToUser(destinations = "/queue/errors", broadcast = false)  // 해당 Session 의 User 에게만 예외 메시지 전송
    protected ApiResponse<?> handleChatBlockedException(ChatException.ChatBlockedException e) {
        return ApiResponse.error(e.getMessage(), TOO_MANY_MESSAGES);
    }
}
