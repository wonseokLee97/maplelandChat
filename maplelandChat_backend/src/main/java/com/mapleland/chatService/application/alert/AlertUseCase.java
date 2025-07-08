package com.mapleland.chatService.application.alert;

import com.mapleland.chatService.application.alert.service.CreateAlertService;
import com.mapleland.chatService.presentation.websocket.dto.AlertRequestDto;
import com.mapleland.chatService.presentation.websocket.dto.AlertResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlertUseCase {
    private final CreateAlertService createAlertService;

    public AlertResponseDto createAlert(AlertRequestDto requestDto) {
        return createAlertService.execute(requestDto);
    }
}
