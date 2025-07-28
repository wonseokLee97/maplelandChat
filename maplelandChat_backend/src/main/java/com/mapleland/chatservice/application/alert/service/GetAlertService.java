package com.mapleland.chatservice.application.alert.service;

import com.mapleland.chatservice.presentation.websocket.dto.AlertResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAlertService {
    private final AlertDomainService alertDomainService;

    public List<AlertResponseDto> execute(String uuid) {
        return alertDomainService.getAlerts(uuid);
    }
}
