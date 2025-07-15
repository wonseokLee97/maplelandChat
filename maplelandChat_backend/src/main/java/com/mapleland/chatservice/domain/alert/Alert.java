package com.mapleland.chatservice.domain.alert;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "alert")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Alert {

    @Id
    private ObjectId id;

    @Field("item_name")
    private String itemName;

    @Field("item_id")
    private String itemId;

    @Field("target_uuid")
    private String targetUuid;

    @Field("sender")
    private String sender;

    @Field("message")
    private String message;

    @Field("is_checked")
    private boolean isChecked;

    @Builder
    private Alert(String message, String sender, String targetUuid, String itemName, String itemId) {
        this.message = message;
        this.sender = sender;
        this.targetUuid = targetUuid;
        this.itemName = itemName;
        this.itemId = itemId;
    }
}
