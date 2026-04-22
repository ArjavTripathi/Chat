package com.chat.aj.chatbackend.entities;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@Document(collection = "groups")
public class Group {
    @Id
    private String id;
    private String groupName;
    private String ownerId;

    private List<String> memberIds = new ArrayList<>();
    private List<String> moderatorIds = new ArrayList<>();

    public void addToMembers(String id){
        if(!memberIds.contains(id)){
            memberIds.add(id);
        }
    }

    public void addToModerators(String id){
        if(!moderatorIds.contains(id)){
            moderatorIds.add(id);
        }
    }
}
