package com.chat.aj.chatbackend.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    private Long id;
    private String username;
    private String password;
    private String email;
    private String role = "ROLE_USER";
}
