package com.mapleland.chatService.infrastructure.chat.mongo;

import com.mapleland.chatService.domain.chat.Chat;
import com.mapleland.chatService.infrastructure.chat.ChatRepository;
import com.mongodb.client.MongoClient;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Repository
public class MongoChatRepositoryImpl implements ChatRepository {
    private final MongoTemplate mongoTemplate;
    private final MongoClient mongo;

    @Override
    public Chat save(Chat chat) {
        return mongoTemplate.save(chat);
    }

    // 1. 최초 로딩
    // SELECT * FROM chat
    // WHERE item_id = 'itemId'
    // ORDER BY created_at DESC LIMIT 10;

    // 2. Cursor 로딩
    // SELECT * FROM chat
    // WHERE item_id = 'itemId' AND created_at < cursor
    // ORDER BY created_at DESC LIMIT 10;

    // 3. 조건 검색
    // SELECT * FROM chat
    // WHERE item_id = 'itemId' and message like %search%
    // ORDER BY created_at DESC LIMIT 10;
    @Override
    public List<Chat> getChatsByCursor(String itemId, int limit, String cursor, String search) {
        Criteria criteria = Criteria.where("item_id").is(itemId);

        if (!"null".equals(search)) {
            String escapedSearch = Pattern.quote(search);
            criteria = criteria.and("message").regex(escapedSearch, "i");
        }

        if (!"null".equals(cursor)) {
            criteria = criteria.and("_id").lt(cursor);
        }

        Query query = new Query(criteria)
                .with(Sort.by(Sort.Direction.DESC, "_id"))
                .limit(limit);

        return mongoTemplate.find(query, Chat.class);
    }

    @Override
    public Chat modifyChat(String id) {
        return null;
    }
}