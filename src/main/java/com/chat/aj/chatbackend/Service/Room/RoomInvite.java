package com.chat.aj.chatbackend.Service.Room;

import com.chat.aj.chatbackend.Exceptions.BadRequestException;
import com.chat.aj.chatbackend.Exceptions.ConflictException;
import com.chat.aj.chatbackend.Exceptions.ResourceNotFoundException;
import com.chat.aj.chatbackend.Respositories.GroupInviteRepository;
import com.chat.aj.chatbackend.Respositories.GroupRepository;
import com.chat.aj.chatbackend.entities.Group;
import com.chat.aj.chatbackend.entities.GroupInvite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RoomInvite {
    private final GroupInviteRepository groupInviteRepository;
    private final GroupRepository groupRepository;

    @Autowired
    public RoomInvite(GroupInviteRepository groupInviteRepository, GroupRepository groupRepository) {
        this.groupInviteRepository = groupInviteRepository;
        this.groupRepository = groupRepository;
    }



    public GroupInvite sendInvite(String inviteeId, String inviterId, String groupId){

        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found"));

        if (!group.getMemberIds().contains(inviterId)) {
            throw new BadRequestException("You are not a member of this group");
        }

        if (group.getMemberIds().contains(inviteeId) || group.getModeratorIds().contains(inviteeId)) {
            throw new ConflictException("User is already a member");
        }

        GroupInvite invite = new GroupInvite();
        invite.setGroupId(groupId);
        invite.setInviterId(inviterId);
        invite.setInviteeId(inviteeId);
        invite.setStatus(GroupInvite.InviteStatus.PENDING);
        invite.setCreatedAt(LocalDateTime.now());
        return groupInviteRepository.save(invite);
    }

    public void acceptInvite(String inviteId, String currentUserId) {
        GroupInvite invite = groupInviteRepository.findById(inviteId)
                .orElseThrow(() -> new ResourceNotFoundException("Invite not found"));

        if (!invite.getInviteeId().equals(currentUserId)) {
            throw new BadRequestException("Not authorized to accept this invite");
        }

        if (invite.getStatus() != GroupInvite.InviteStatus.PENDING) {
            throw new BadRequestException("Invite is no longer valid");
        }

        invite.setStatus(GroupInvite.InviteStatus.ACCEPTED);
        groupInviteRepository.save(invite);

        Group group = groupRepository.findById(invite.getGroupId())
                .orElseThrow(() -> new ResourceNotFoundException("Group not found"));
        group.getMemberIds().add(currentUserId);
        groupRepository.save(group);
    }
}
