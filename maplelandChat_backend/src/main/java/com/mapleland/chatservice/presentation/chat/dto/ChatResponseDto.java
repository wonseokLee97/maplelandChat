package com.mapleland.chatservice.presentation.chat.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Builder;
import lombok.Getter;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

@Builder
@Getter
public class ChatResponseDto {
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;
    private String itemId;
    private String userName;
    private String message;
    private String imgPath;
    private String replyUserName;
    private String uuid;
    private LocalDateTime createdAt;
}
