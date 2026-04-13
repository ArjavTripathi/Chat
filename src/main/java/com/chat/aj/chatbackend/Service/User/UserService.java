package com.chat.aj.chatbackend.Service.User;

import com.chat.aj.chatbackend.DTO.AppRole;
import com.chat.aj.chatbackend.DTO.LoginRequest;
import com.chat.aj.chatbackend.Respositories.TokenRepository;
import com.chat.aj.chatbackend.Respositories.UserRepository;
import com.chat.aj.chatbackend.Service.Email.EmailService;
import com.chat.aj.chatbackend.entities.User;
import com.chat.aj.chatbackend.security.jwt.JwtAuthenticationResponse;
import com.chat.aj.chatbackend.security.jwt.JwtUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private AuthenticationManager authenticationManager;
    private TokenService tokenService;
    private EmailService emailService;
    private JwtUtils jwtUtils;

    public User registerUser(User user) throws Exception {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(AppRole.ROLE_USER);
        user.setVerified(false);

        User savedUser = userRepository.save(user);

        String token = tokenService.createAndSaveToken(savedUser.getId());
        try {
            emailService.sendEmail(savedUser.getEmail(), token);
        } catch (Exception e) {
            log.error("Error sending email: {}", e.getMessage());
            throw new Exception("Failed to send verification email");
        }

        return savedUser;
    }

    public JwtAuthenticationResponse authenticatenUser(LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String jwt = jwtUtils.generateToken(userDetails);
        return new JwtAuthenticationResponse(jwt);
    }

    public User findByUsername(String username){
        return userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found with username " + username)
        );
    }

    public User findById(String id){
        return userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User not found with id " + id)
        );
    }

    public void setVerified(User user){
        user.setVerified(true);
        userRepository.save(user);
    }
}
