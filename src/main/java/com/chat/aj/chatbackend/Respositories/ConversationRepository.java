package com.chat.aj.chatbackend.Respositories;

import com.chat.aj.chatbackend.entities.Conversation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConversationRepository extends MongoRepository<Conversation, String> {
    Optional<Conversation> findById(String id);
}
