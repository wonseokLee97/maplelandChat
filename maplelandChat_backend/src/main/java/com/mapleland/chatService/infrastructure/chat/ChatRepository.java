package com.mapleland.chatService.infrastructure.chat;

import com.mapleland.chatService.domain.chat.Chat;

import java.util.List;

public interface ChatRepository {
    Chat save(Chat chat);
    List<Chat> getChatsByCursor(String itemId, int limit, String cursor, String search);
    Chat modifyChat(String id);
}
