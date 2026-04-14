package com.chat.aj.chatbackend.Controller;

import com.chat.aj.chatbackend.DTO.LoginRequest;
import com.chat.aj.chatbackend.DTO.RegisterRequest;
import com.chat.aj.chatbackend.Service.User.TokenService;
import com.chat.aj.chatbackend.Service.User.UserService;
import com.chat.aj.chatbackend.entities.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
@Slf4j
public class UserController {

    private UserService userService;

    private TokenService tokenService;

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

    @GetMapping("/public/verify")
    public ResponseEntity<?> verify(@RequestParam String token) throws TimeoutException {
        tokenService.verifyToken(token);
        return ResponseEntity.ok("Verified");
    }
}
