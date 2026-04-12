package com.chat.aj.chatbackend.Service.User;

import com.chat.aj.chatbackend.DTO.AppRole;
import com.chat.aj.chatbackend.DTO.LoginRequest;
import com.chat.aj.chatbackend.Respositories.UserRepository;
import com.chat.aj.chatbackend.entities.User;
import com.chat.aj.chatbackend.security.jwt.JwtAuthenticationResponse;
import com.chat.aj.chatbackend.security.jwt.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private AuthenticationManager authenticationManager;
    private JwtUtils jwtUtils;

    public User registerUser(User user){

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(AppRole.ROLE_USER);
        return userRepository.save(user);
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
}
