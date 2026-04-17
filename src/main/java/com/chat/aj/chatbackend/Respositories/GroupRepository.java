package com.chat.aj.chatbackend.Respositories;

import com.chat.aj.chatbackend.entities.Group;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GroupRepository extends MongoRepository<Group, String> {
    Group findByGroupName(String GroupName);
}
