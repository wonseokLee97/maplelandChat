package com.mapleland.chatservice.presentation.websocket.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
public class ChatRequestDto {
    private String itemId;
    private String userName;
    @NotBlank(message = "메시지를 입력해주세요")
    @Size(max = 200, message = "메시지는 최대 200자까지 입력할 수 있습니다.")
    @Setter
    private String message;
    @Setter
    private String uuid;
    private String imgPath;
    private String replyUserName;
}
