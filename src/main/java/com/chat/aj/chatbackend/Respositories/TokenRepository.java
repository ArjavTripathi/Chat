package com.chat.aj.chatbackend.Respositories;

import com.chat.aj.chatbackend.entities.Token;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends MongoRepository<Token, String> {
    Optional<Token> findTokenByToken(String token);
}
