package com.chat.aj.chatbackend.entities;

import com.chat.aj.chatbackend.DTO.AppRole;
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
    private AppRole role;

    public User(Long id, String username, String password, AppRole role, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
    }

    public User(Long id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = AppRole.ROLE_USER;
    }
}
