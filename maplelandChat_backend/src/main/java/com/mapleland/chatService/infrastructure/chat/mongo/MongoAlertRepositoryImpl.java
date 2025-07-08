package com.mapleland.chatService.infrastructure.chat.mongo;

import com.mapleland.chatService.domain.alert.Alert;
import com.mapleland.chatService.infrastructure.chat.AlertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class MongoAlertRepositoryImpl implements AlertRepository {
    private final MongoTemplate mongoTemplate;

    @Override
    public Alert save(Alert alert) {
        return mongoTemplate.save(alert);
    }
}
