package com.chat.aj.chatbackend.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Getter
@Setter
public class Token {
    @Id
    public String tid;
    public String token;
    public String targetId;
    public boolean isUsed;
    public boolean isExpired;
    public LocalDateTime timeCreated;
}
