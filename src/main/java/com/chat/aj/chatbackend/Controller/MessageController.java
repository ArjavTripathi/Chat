package com.chat.aj.chatbackend.Controller;

import com.chat.aj.chatbackend.Respositories.MessageRepository;
import com.chat.aj.chatbackend.entities.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/messages")
public class MessageController {
    private final MessageRepository messageRepository;

    @Autowired
    public MessageController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<Message>> getGroupMessages(
            @PathVariable String groupId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("timestamp").descending());
        return ResponseEntity.ok(messageRepository.findByGroupIdOrderByTimestampDesc(groupId, pageable).getContent());
    }

    @GetMapping("/dm/{conversationId}")
    public ResponseEntity<List<Message>> getDmMessages(
            @PathVariable String conversationId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("timestamp").descending());
        return ResponseEntity.ok(messageRepository.findByGroupIdOrderByTimestampDesc(conversationId, pageable).getContent());
    }
}
