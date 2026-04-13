package com.chat.aj.chatbackend.Service.User;

import com.chat.aj.chatbackend.Respositories.TokenRepository;
import com.chat.aj.chatbackend.Respositories.UserRepository;
import com.chat.aj.chatbackend.entities.Token;
import com.chat.aj.chatbackend.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeoutException;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;

    public String createAndSaveToken(String userId){
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int length = 15;
        StringBuilder sb = new StringBuilder(length);
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }

        Token token = new Token();
        token.setTimeCreated(LocalDateTime.now());
        token.setTargetId(userId);
        token.setToken(sb.toString());
        token.setUsed(false);
        token.setExpired(false);
        tokenRepository.save(token);
        return sb.toString();
    }

    public void verifyToken(String t) throws TimeoutException {
        Optional<Token> token = tokenRepository.findTokenByToken(t);
        if (token.isEmpty()) {
            throw new AuthenticationCredentialsNotFoundException("Token not found");
        }
        Token tok = token.get();
        long minutesBetween = Math.abs(ChronoUnit.MINUTES.between(LocalDateTime.now(), tok.getTimeCreated()));

        if (tok.isExpired() || tok.isUsed() || minutesBetween >= 30) {
            tok.setExpired(true);
            tokenRepository.save(tok);
            throw new TimeoutException("Token expired.");
        }

        tok.setUsed(true);
        tokenRepository.save(tok);

        User user = userRepository.findById(tok.getTargetId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setVerified(true);
        userRepository.save(user);
    }
}
