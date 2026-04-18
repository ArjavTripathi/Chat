package com.chat.aj.chatbackend.Controller;

import com.chat.aj.chatbackend.DTO.GroupCreationRequest;
import com.chat.aj.chatbackend.Respositories.ConversationRepository;
import com.chat.aj.chatbackend.Respositories.GroupRepository;
import com.chat.aj.chatbackend.Service.Room.ConversationService;
import com.chat.aj.chatbackend.Service.Room.GroupService;
import com.chat.aj.chatbackend.Service.User.UserService;
import com.chat.aj.chatbackend.entities.Group;
import com.chat.aj.chatbackend.entities.User;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/groups")
public class RoomController { //Make group, start dm, close dm, delete group,
    private final GroupService groupService;
    private final UserService userService;
    private final ConversationService conversationService;

    @Autowired
    public RoomController(GroupService groupService, UserService userService, ConversationService conversationService) {
        this.groupService = groupService;
        this.userService = userService;
        this.conversationService = conversationService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createNewGroup(@RequestParam String groupName, Principal principal){
        groupService.createNewGroup(groupName, principal.getName());
        return ResponseEntity.ok("Created!");
    }

    @GetMapping
    public ResponseEntity<List<Group>> getGroups(Principal principal){
        List<Group> myGroups = groupService.getGroups(principal.getName());
        return ResponseEntity.ok(myGroups);
    }
}
