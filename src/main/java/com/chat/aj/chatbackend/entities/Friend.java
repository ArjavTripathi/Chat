package com.chat.aj.chatbackend.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "friends")
@Getter @Setter @NoArgsConstructor
public class Friend {
    @Id
    private String id;
    private String senderId;
    private String receiverId;
    private LocalDateTime timestamp;
    private FriendShipStatus status;

    public enum FriendShipStatus {
        PENDING,
        ACCEPTED,
        REJECTED
    }
}
