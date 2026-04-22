package com.chat.aj.chatbackend.Service.Room;

import com.chat.aj.chatbackend.Exceptions.BadRequestException;
import com.chat.aj.chatbackend.Exceptions.ConflictException;
import com.chat.aj.chatbackend.Exceptions.ResourceNotFoundException;
import com.chat.aj.chatbackend.Respositories.GroupInviteRepository;
import com.chat.aj.chatbackend.Respositories.GroupRepository;
import com.chat.aj.chatbackend.Service.User.UserService;
import com.chat.aj.chatbackend.entities.Group;
import com.chat.aj.chatbackend.entities.GroupInvite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;

@Service
public class RoomInviteService {
    private final GroupInviteRepository groupInviteRepository;
    private final GroupRepository groupRepository;
    private final GroupService groupService;
    private final UserService userService;

    @Autowired
    public RoomInviteService(GroupInviteRepository groupInviteRepository, GroupRepository groupRepository, GroupService groupService, UserService userService) {
        this.groupInviteRepository = groupInviteRepository;
        this.groupRepository = groupRepository;
        this.groupService = groupService;
        this.userService = userService;
    }

    public String createLink(){
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int length = 6;
        StringBuilder sb = new StringBuilder(length);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }
        return sb.toString();
    }



    public GroupInvite createInvite(String groupId){

        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found"));

        GroupInvite invite = new GroupInvite();
        invite.setGroupId(groupId);
        invite.setLink(createLink());
        invite.setStatus(GroupInvite.InviteStatus.PENDING);
        invite.setCreatedAt(LocalDateTime.now());
        return groupInviteRepository.save(invite);
    }

    public void acceptInvite(String inviteId, String currentUserName) {
        String userId = userService.findByUsername(currentUserName).getId();

        GroupInvite invite = groupInviteRepository.findByLink(inviteId);

        if(invite == null){
            throw new ResourceNotFoundException("Could not find invite");
        }

        if (invite.getStatus() != GroupInvite.InviteStatus.PENDING) {
            throw new BadRequestException("Invite is no longer valid");
        }

        long hours = ChronoUnit.HOURS.between(invite.getCreatedAt(), LocalDateTime.now());

        if(Math.abs(hours) > 25){
            invite.setStatus(GroupInvite.InviteStatus.EXPIRED);
            groupInviteRepository.save(invite);
            throw new BadRequestException("Invite Expired");
        }

        Group group = groupRepository.findById(invite.getGroupId())
                .orElseThrow(() -> new ResourceNotFoundException("Group not found"));

        if(groupService.checkIfMemberInGroup(userId, group.getId())){
            throw new ConflictException("You are already in this group!");
        }


        invite.setStatus(GroupInvite.InviteStatus.ACCEPTED);
        groupInviteRepository.save(invite);
        group.getMemberIds().add(userId);
        groupRepository.save(group);
    }
}
