package com.chat.aj.chatbackend.DTO;

import lombok.Data;

import java.util.List;

@Data
public class GroupCreationRequest {
    public String ownerid;
    public String groupName;
}
