package com.mapleland.chatservice.presentation.websocket.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Builder;
import lombok.Getter;
import org.bson.types.ObjectId;

@Builder
@Getter
public class AlertResponseDto {
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;
    private String message;
    private String sender;
    private String itemName;
    private String itemId;
}