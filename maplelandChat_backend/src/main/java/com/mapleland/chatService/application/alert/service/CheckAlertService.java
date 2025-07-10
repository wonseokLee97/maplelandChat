package com.mapleland.chatService.application.alert.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckAlertService {
    private final AlertDomainService alertDomainService;

    public Boolean execute(String alertId) {
        return alertDomainService.patchAlertAsChecked(alertId);
    }
}
