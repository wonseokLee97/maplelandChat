package com.mapleland.chatservice.infrastructure.chat.mongo;

import com.mapleland.chatservice.domain.alert.Alert;
import com.mapleland.chatservice.infrastructure.chat.AlertRepository;
import com.mongodb.client.MongoClient;
import com.mongodb.client.result.UpdateResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class MongoAlertRepositoryImpl implements AlertRepository {
    private final MongoTemplate mongoTemplate;
    private final MongoClient mongo;

    @Override
    public Alert save(Alert alert) {
        return mongoTemplate.save(alert);
    }

    @Override
    public List<Alert> getAlerts(String uuid) {
        Criteria criteria = Criteria.where("target_uuid").is(uuid).and("isChecked").is(false);

        Query query = new Query(criteria)
                .with(Sort.by(Sort.Direction.DESC, "_id"))
                .limit(10);

        return mongoTemplate.find(query, Alert.class);
    }

    @Override
    public Boolean patchAlertAsChecked(String alertId) {
        Criteria criteria = Criteria.where("_id").is(alertId);
        Query query = new Query(criteria);
        Update update = new Update().set("is_checked", true);

        UpdateResult result = mongoTemplate.updateFirst(query, update, Alert.class);
        return result.getModifiedCount() > 0;
    }
}
