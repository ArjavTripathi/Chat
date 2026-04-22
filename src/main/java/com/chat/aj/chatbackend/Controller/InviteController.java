package com.chat.aj.chatbackend.Controller;

import com.chat.aj.chatbackend.Service.Room.RoomInviteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/i")
public class InviteController {

    private final RoomInviteService roomInviteService;

    @Autowired
    public InviteController(RoomInviteService roomInviteService) {
        this.roomInviteService = roomInviteService;
    }

    @PutMapping("{link}")
    public ResponseEntity<?> acceptInvite(Principal principal, @PathVariable String link){
        roomInviteService.acceptInvite(link, principal.getName());
        return ResponseEntity.ok().build();
    }
}
