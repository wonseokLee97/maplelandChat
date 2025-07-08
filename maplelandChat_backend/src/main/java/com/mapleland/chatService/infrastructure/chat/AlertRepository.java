package com.mapleland.chatService.infrastructure.chat;

import com.mapleland.chatService.domain.alert.Alert;

public interface AlertRepository {
    Alert save(Alert alert);
}
