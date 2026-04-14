package com.chat.aj.chatbackend.entities;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "messages")
@Getter
@Setter
@NoArgsConstructor
public class Message {
    @Id
    private String id;
    private String groupId;
    private String senderId;
    private String content;
    private LocalDateTime timestamp;
    private MessageType type;
    public enum MessageType { GROUP, DM }
}
