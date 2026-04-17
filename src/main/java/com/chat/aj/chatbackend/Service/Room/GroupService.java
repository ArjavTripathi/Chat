package com.chat.aj.chatbackend.Service.Room;

import com.chat.aj.chatbackend.DTO.GroupCreationRequest;
import com.chat.aj.chatbackend.Exceptions.BadRequestException;
import com.chat.aj.chatbackend.Respositories.GroupRepository;
import com.chat.aj.chatbackend.Service.User.UserService;
import com.chat.aj.chatbackend.entities.Group;
import com.chat.aj.chatbackend.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupService {
    private final UserService userService;
    private final GroupRepository groupRepository;

    @Autowired
    public GroupService(UserService userService, GroupRepository groupRepository) {
        this.userService = userService;
        this.groupRepository = groupRepository;
    }



    public void createNewGroup(GroupCreationRequest newgrp) {
        if(userService.findById(newgrp.getOwnerid()) != null){
            Group group = new Group();
            group.setGroupName(newgrp.getGroupName());
            group.setOwnerId(newgrp.getOwnerid());
            groupRepository.save(group);
        }
    }
}
