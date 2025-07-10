package com.mapleland.chatService.application.alert;

import com.mapleland.chatService.application.alert.service.CheckAlertService;
import com.mapleland.chatService.application.alert.service.CreateAlertService;
import com.mapleland.chatService.application.alert.service.GetAlertService;
import com.mapleland.chatService.presentation.websocket.dto.AlertRequestDto;
import com.mapleland.chatService.presentation.websocket.dto.AlertResponseDto;
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
