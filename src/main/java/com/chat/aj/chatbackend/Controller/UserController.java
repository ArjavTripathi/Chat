package com.chat.aj.chatbackend.Controller;

import com.chat.aj.chatbackend.DTO.LoginRequest;
import com.chat.aj.chatbackend.DTO.RegisterRequest;
import com.chat.aj.chatbackend.Service.User.UserService;
import com.chat.aj.chatbackend.entities.User;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @PostMapping("/public/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest){
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(registerRequest.getPassword());
        user.setEmail(registerRequest.getEmail());
        userService.registerUser(user);
        return ResponseEntity.ok("User Registered Successfully!");
    }

    @PostMapping("/public/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(userService.authenticatenUser(loginRequest));
    }
}
