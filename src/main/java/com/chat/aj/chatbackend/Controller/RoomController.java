package com.chat.aj.chatbackend.Controller;

import com.chat.aj.chatbackend.Service.Room.ConversationService;
import com.chat.aj.chatbackend.Service.Room.GroupService;
import com.chat.aj.chatbackend.Service.Room.RoomInviteService;
import com.chat.aj.chatbackend.Service.User.UserService;
import com.chat.aj.chatbackend.entities.Group;
import com.chat.aj.chatbackend.entities.GroupInvite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private final RoomInviteService roomInviteService;
    private final String frontendurl;

    @Autowired
    public RoomController(GroupService groupService, UserService userService, ConversationService conversationService, RoomInviteService roomInviteService, @Value("${frontend.url}") String frontendurl) {
        this.groupService = groupService;
        this.userService = userService;
        this.conversationService = conversationService;
        this.roomInviteService = roomInviteService;
        this.frontendurl = frontendurl;
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

    @DeleteMapping
    public ResponseEntity<?> deleteGroup(Principal principal, @RequestParam String groupId){
        groupService.deleteGroup(principal.getName(), groupId);
        return ResponseEntity.ok("Group deleted");
    }

    @PostMapping("/invites/create")
    public ResponseEntity<?> createInvite(Principal principal, @RequestParam(required = true) String groupId){
        GroupInvite invite = roomInviteService.createInvite(groupId);
        return ResponseEntity.ok(frontendurl + "/i/" + invite.getLink());
    }

    @PutMapping("/leave")
    public ResponseEntity<?> leaveGroup(Principal principal, @RequestParam(required = true) String groupId){
        groupService.leaveGroup(principal.getName(), groupId);
        return ResponseEntity.ok().build();
    }

}
