package com.chat.aj.chatbackend.Service.User;

import com.chat.aj.chatbackend.Exceptions.BadRequestException;
import com.chat.aj.chatbackend.Exceptions.ConflictException;
import com.chat.aj.chatbackend.Exceptions.ResourceNotFoundException;
import com.chat.aj.chatbackend.Respositories.FriendRepository;
import com.chat.aj.chatbackend.entities.Friend;
import com.chat.aj.chatbackend.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class FriendService {
    private final UserService userService;
    private final FriendRepository friendRepository;

    private void checkUser(String id){
        userService.findById(id);
    }

    @Autowired
    public FriendService(UserService userService, FriendRepository friendRepository) {
        this.userService = userService;
        this.friendRepository = friendRepository;
    }

    public Friend sendFriendRequest(String senderId, String receiverId){
        checkUser(senderId);
        checkUser(receiverId);

        if (friendRepository.existsBySenderIdAndReceiverIdOrReceiverIdAndSenderId(
                senderId, receiverId, senderId, receiverId)) {
            throw new ConflictException("Friendship already exists");
        }

        Friend friend = new Friend();
        friend.setSenderId(senderId);
        friend.setReceiverId(receiverId);
        friend.setTimestamp(LocalDateTime.now());
        return friendRepository.save(friend);
    }

    public Friend acceptFriendRequest(String friendshipId, String userId){
        Friend friendship = friendRepository.findById(friendshipId)
                .orElseThrow(() -> new ResourceNotFoundException("Request not found"));

        if (!friendship.getReceiverId().equals(userId)) {
            throw new BadRequestException("Not authorized to accept this request");
        }

        friendship.setStatus(Friend.FriendShipStatus.ACCEPTED);
        return friendRepository.save(friendship);
    }
}
