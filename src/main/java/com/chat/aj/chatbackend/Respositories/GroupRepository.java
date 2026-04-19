package com.chat.aj.chatbackend.Respositories;

import com.chat.aj.chatbackend.entities.Group;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface GroupRepository extends MongoRepository<Group, String> {
    Group findByGroupName(String GroupName);
    List<Group> findByOwnerId(String ownerId);
    List<Group> findByMemberIdsContainingOrModeratorIdsContaining(String memberId, String moderatorId);
    boolean existsByIdAndModeratorIds(String groupId, String userId);
    boolean existsByIdAndMemberIds(String groupId, String userId);
}
