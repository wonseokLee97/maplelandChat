package com.mapleland.chatservice.application.alert.service;

import com.mapleland.chatservice.application.alert.helper.AlertMapper;
import com.mapleland.chatservice.domain.alert.Alert;
import com.mapleland.chatservice.infrastructure.chat.AlertRepository;
import com.mapleland.chatservice.presentation.websocket.dto.AlertRequestDto;
import com.mapleland.chatservice.presentation.websocket.dto.AlertResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<AlertResponseDto> getAlerts(String uuid) {
        return alertRepository.getAlerts(uuid).stream()
                .map(alertMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public Boolean patchAlertAsChecked(String alertId) {
        return alertRepository.patchAlertAsChecked(alertId);
    }
}
