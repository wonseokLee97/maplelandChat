package com.mapleland.chatservice.infrastructure.chat;

import com.mapleland.chatservice.domain.alert.Alert;

import java.util.List;

public interface AlertRepository {
    Alert save(Alert alert);
    List<Alert> getAlerts(String uuid);
    Boolean patchAlertAsChecked(String alertId);
}
