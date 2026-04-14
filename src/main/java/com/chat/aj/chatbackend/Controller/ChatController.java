package com.chat.aj.chatbackend.Controller;


import com.chat.aj.chatbackend.DTO.MessageRequest;
import com.chat.aj.chatbackend.Respositories.ConversationRepository;
import com.chat.aj.chatbackend.Respositories.GroupRepository;
import com.chat.aj.chatbackend.Respositories.MessageRepository;
import com.chat.aj.chatbackend.Respositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

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

    }

}

