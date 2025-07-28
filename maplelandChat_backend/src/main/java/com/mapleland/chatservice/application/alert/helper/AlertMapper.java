package com.mapleland.chatservice.application.alert.helper;

import com.mapleland.chatservice.domain.alert.Alert;
import com.mapleland.chatservice.presentation.websocket.dto.AlertRequestDto;
import com.mapleland.chatservice.presentation.websocket.dto.AlertResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AlertMapper {
    Alert toEntity(AlertRequestDto requestDto);
    AlertResponseDto toResponseDto(Alert alert);
}
