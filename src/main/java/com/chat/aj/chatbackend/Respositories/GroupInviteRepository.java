package com.chat.aj.chatbackend.Respositories;

import com.chat.aj.chatbackend.entities.GroupInvite;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupInviteRepository extends MongoRepository<GroupInvite, String> {
    GroupInvite findGroupInviteByStatus(GroupInvite.InviteStatus status);

    GroupInvite findByLink(String link);
}
