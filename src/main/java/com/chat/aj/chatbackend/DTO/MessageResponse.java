package com.chat.aj.chatbackend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class MessageResponse {
    private String id;
    private String roomId;
    private String senderId;
    private String senderUsername;
    private String content;
    private LocalDateTime timestamp;
}
