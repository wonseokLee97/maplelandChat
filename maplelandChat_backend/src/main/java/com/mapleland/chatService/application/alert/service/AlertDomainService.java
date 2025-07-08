package com.mapleland.chatService.application.alert.service;

import com.mapleland.chatService.application.alert.helper.AlertMapper;
import com.mapleland.chatService.domain.alert.Alert;
import com.mapleland.chatService.infrastructure.chat.AlertRepository;
import com.mapleland.chatService.presentation.websocket.dto.AlertRequestDto;
import com.mapleland.chatService.presentation.websocket.dto.AlertResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlertDomainService {

    private final AlertRepository alertRepository;
    private final AlertMapper alertMapper;

    public AlertResponseDto createAlert(AlertRequestDto alertRequestDto) {
        Alert alert = alertMapper.toEntity(alertRequestDto);
        Alert save = alertRepository.save(alert);

        return alertMapper.toResponseDto(save);
    }
}
