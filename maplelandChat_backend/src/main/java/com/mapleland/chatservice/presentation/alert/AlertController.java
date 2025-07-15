package com.mapleland.chatservice.presentation.alert;

import com.mapleland.chatservice.application.alert.AlertUseCase;
import com.mapleland.chatservice.shared.api.ApiResponse;
import com.mapleland.chatservice.shared.jwt.TokenClaims;
import com.mapleland.chatservice.shared.type.http.HttpSuccessType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alert")
@RequiredArgsConstructor
public class AlertController {

    private final AlertUseCase alertUseCase;

    @GetMapping
    public ApiResponse<?> getAlertList() {
        return ApiResponse.success(
                alertUseCase.getAlerts(((TokenClaims) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()),
                HttpSuccessType.SUCCESS_GET_ALERT_LIST
        );
    }

    @PatchMapping("/{alertId}/check")
    public ApiResponse<?> patchAlertAsChecked(@PathVariable("alertId") String alertId){
        return ApiResponse.success(
                alertUseCase.patchAlertAsChecked(alertId),
                HttpSuccessType.SUCCESS_GET_CHAT_LIST
        );
    }
}
