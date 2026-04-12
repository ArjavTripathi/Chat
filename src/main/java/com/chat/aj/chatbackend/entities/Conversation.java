package com.chat.aj.chatbackend.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "conversations")
@Getter
@Setter
public class Conversation {
    @Id
    private String id;
    private List<String> participantIds;
}
