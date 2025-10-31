package com.springai.demo.controllers;

import com.springai.demo.services.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class ChatController {

    private ChatService chatService;

    public ChatController(ChatService chatService){
        this.chatService=chatService;
    }

    @PostMapping("chat")
    public ResponseEntity<String> getResponse(@RequestParam(value = "q", required = true) String userQuery){
        return ResponseEntity.ok(chatService.getResponse(userQuery));
    }
}
