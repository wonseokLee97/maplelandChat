package com.mapleland.chatservice.infrastructure.chat;

import com.mapleland.chatservice.domain.chat.Chat;

import java.util.List;

public interface ChatRepository {
    Chat save(Chat chat);
    List<Chat> getChatsByCursor(String itemId, int limit, String cursor, String search);
    Chat modifyChat(String id);
}
