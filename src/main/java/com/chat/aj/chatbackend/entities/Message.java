package com.chat.aj.chatbackend.entities;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Getter
@Setter
@NoArgsConstructor
public class Message {
    private User sender;
    private String content;
    private LocalDateTime timestamp;

    public Message(String content, User sender) {
        this.content = content;
        this.sender = sender;
        this.timestamp = LocalDateTime.now();
    }
}
