package com.mapleland.chatservice.domain.chat;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Data
@Document(collection = "chat")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Chat {

    @Id
    private ObjectId id;

    @Field("item_id")
    private String itemId;

    @Field("uuid")
    private String uuid;

    @Field("user_name")
    private String userName;

//    @Field("name_tag")
//    private String nameTag;
//
//    @Field("ip_address")
//    private String ipAddress;

    @Field("message")
    private String message;

    @Field("img_path")
    private String imgPath;

    @Field("created_at")
    private LocalDateTime createdAt;

    @Field("del")
    private int del;

    @Field("ban")
    private boolean ban;

    @Builder
    private Chat(String itemId, String userName, String message, String imgPath, String uuid) {
        this.itemId = itemId;
        this.userName = userName;
        this.message = message;
        this.imgPath = imgPath;
        this.uuid = uuid;
        this.createdAt = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    }
}
