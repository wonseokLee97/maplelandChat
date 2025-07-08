package com.mapleland.chatService.application.alert.service;

import com.mapleland.chatService.presentation.websocket.dto.AlertRequestDto;
import com.mapleland.chatService.presentation.websocket.dto.AlertResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateAlertService {
    private final AlertDomainService alertDomainService;

    public AlertResponseDto execute(AlertRequestDto alertRequestDto) {
        return alertDomainService.createAlert(alertRequestDto);
    }
}
