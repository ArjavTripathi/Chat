package com.chat.aj.chatbackend.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "group_invites")
@Getter @Setter @NoArgsConstructor
public class GroupInvite {
    @Id
    private String id;
    private String link
    private String groupId;
    private String inviterId;
    private String inviteeId;
    private InviteStatus status;
    private LocalDateTime createdAt;

    public enum InviteStatus{
        PENDING,
        ACCEPTED,
        DECLINED,
        EXPIRED
    }
}
