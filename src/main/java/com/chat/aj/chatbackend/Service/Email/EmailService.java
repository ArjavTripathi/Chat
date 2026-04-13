package com.chat.aj.chatbackend.Service.Email;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;

    @Value("${frontend.url}")
    private String url;

    private String fullurl;

    @PostConstruct
    public void init() {
        this.fullurl = url + "/api/v1/users/public/verify?token=";
    }

    public void sendEmail(String to, String verificationToken) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Your ChatNow verification link");
        message.setText("Here is the link for verificaiton. It will expire in 30 minutes. " + fullurl + verificationToken);
        mailSender.send(message);
    }


}
