package com.chat.aj.chatbackend.Respositories;

import com.chat.aj.chatbackend.entities.Group;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RootRepository extends MongoRepository<Group, String> {
    Group findByGroupId(String GroupId);
}
