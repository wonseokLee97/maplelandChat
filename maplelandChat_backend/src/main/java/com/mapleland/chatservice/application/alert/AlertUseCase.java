package com.mapleland.chatservice.application.alert;

import com.mapleland.chatservice.application.alert.service.CheckAlertService;
import com.mapleland.chatservice.application.alert.service.CreateAlertService;
import com.mapleland.chatservice.application.alert.service.GetAlertService;
import com.mapleland.chatservice.presentation.websocket.dto.AlertRequestDto;
import com.mapleland.chatservice.presentation.websocket.dto.AlertResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlertUseCase {
    private final CreateAlertService createAlertService;
    private final GetAlertService getAlertService;
    private final CheckAlertService checkAlertService;

    public AlertResponseDto createAlert(AlertRequestDto requestDto) {
        return createAlertService.execute(requestDto);
    }

    public List<AlertResponseDto> getAlerts(String uuid) {
        return getAlertService.execute(uuid);
    }

    public Boolean patchAlertAsChecked(String alertId) {
        return checkAlertService.execute(alertId);
    };
}
