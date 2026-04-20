package com.chat.aj.chatbackend.Respositories;

import com.chat.aj.chatbackend.entities.Friend;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRepository extends MongoRepository<Friend, String> {
    Optional<Friend> findBySenderIdAndReceiverId(String senderId, String receiverId);

    List<Friend> findByReceiverIdAndStatus(String receiverId, Friend.FriendShipStatus status);
    List<Friend> findBySenderIdAndStatus(String senderId, Friend.FriendShipStatus status);

    boolean existsBySenderIdAndReceiverIdOrReceiverIdAndSenderId(
            String s1, String r1, String s2, String r2);
}
