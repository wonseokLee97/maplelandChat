package com.mapleland.chatService.infrastructure.chat;

import com.mapleland.chatService.domain.alert.Alert;

import java.util.List;

public interface AlertRepository {
    Alert save(Alert alert);
    List<Alert> getAlerts(String uuid);
    Boolean patchAlertAsChecked(String alertId);
}
