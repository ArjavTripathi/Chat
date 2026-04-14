package com.chat.aj.chatbackend.Respositories;

import com.chat.aj.chatbackend.entities.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends MongoRepository<Message, String> {
    List<Message> findByRoomIdOrderByTimestampAsc(String roomId);
    Page<Message> findByRoomIdOrderByTimestampDesc(String roomId, Pageable pageable);
}
