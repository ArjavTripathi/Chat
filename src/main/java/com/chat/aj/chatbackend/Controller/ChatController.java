package com.chat.aj.chatbackend.Controller;


import com.chat.aj.chatbackend.DTO.MessageRequest;
import com.chat.aj.chatbackend.DTO.MessageResponse;
import com.chat.aj.chatbackend.Exceptions.BadRequestException;
import com.chat.aj.chatbackend.Exceptions.ResourceNotFoundException;
import com.chat.aj.chatbackend.Respositories.ConversationRepository;
import com.chat.aj.chatbackend.Respositories.GroupRepository;
import com.chat.aj.chatbackend.Respositories.MessageRepository;
import com.chat.aj.chatbackend.Respositories.UserRepository;
import com.chat.aj.chatbackend.entities.Group;
import com.chat.aj.chatbackend.entities.Message;
import com.chat.aj.chatbackend.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.time.LocalDateTime;

@Controller
public class ChatController {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final ConversationRepository conversationRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public ChatController(MessageRepository messageRepository, UserRepository userRepository, GroupRepository groupRepository, ConversationRepository conversationRepository, SimpMessagingTemplate messagingTemplate) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.conversationRepository = conversationRepository;
        this.messagingTemplate = messagingTemplate;
    }


    @MessageMapping("/group/{groupId}")
    public void sendGroupMessage(
            @DestinationVariable String groupId,
            @Payload MessageRequest request,
            Principal principal
    ){
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException(("Group not found")));

        if (!group.getMemberIds().contains(request.getSender())){
            throw new BadRequestException("User is not a mamber of this group");
        }

        Message message = new Message();
        message.setGroupId(groupId);
        message.setSenderId(request.getSender());
        message.setTimestamp(LocalDateTime.now());
        message.setType(Message.MessageType.GROUP);

        messageRepository.save(message);

        User sender = userRepository.findById(request.getSender())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        MessageResponse response = new MessageResponse(
                message.getId(), groupId, sender.getId(),
                sender.getUsername(), message.getContent(), message.getTimestamp()
        );

        messagingTemplate.convertAndSend("/topic/group/" + groupId, response);
    }

}

