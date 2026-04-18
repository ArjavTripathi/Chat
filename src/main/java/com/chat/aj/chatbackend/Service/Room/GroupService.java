package com.chat.aj.chatbackend.Service.Room;

import com.chat.aj.chatbackend.DTO.GroupCreationRequest;
import com.chat.aj.chatbackend.Exceptions.BadRequestException;
import com.chat.aj.chatbackend.Respositories.GroupRepository;
import com.chat.aj.chatbackend.Service.User.UserService;
import com.chat.aj.chatbackend.entities.Group;
import com.chat.aj.chatbackend.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {
    private final UserService userService;
    private final GroupRepository groupRepository;

    @Autowired
    public GroupService(UserService userService, GroupRepository groupRepository) {
        this.userService = userService;
        this.groupRepository = groupRepository;
    }

    public void createNewGroup(String groupname, String name) {
        User user = userService.findByUsername(name);
        Group group = new Group();
        group.setGroupName(groupname);
        group.setOwnerId(user.getId());
        groupRepository.save(group);
    }

    public List<Group> getGroups(String name) {
            User user = userService.findByUsername(name);
            List<Group> groups = groupRepository.findByOwnerId(user.getId());
            groups.addAll(groupRepository.findByMemberIdsContainingOrModeratorIdsContaining(user.getId(), user.getId()));
            return groups;
    }
}
