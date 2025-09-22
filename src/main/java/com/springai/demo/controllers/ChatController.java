package com.springai.demo.controllers;

import com.springai.demo.entities.AiResponse;
import com.springai.demo.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.io.IOException;

@RestController
@RequestMapping
public class ChatController {

    @Autowired
    private ChatService chatService;

    @GetMapping("chat")
    public ResponseEntity<String> chat(
            @RequestParam(value = "q", required = true) String q) throws IOException {

        String response = chatService.chat(q);
        return ResponseEntity.ok(response);
    }

    @GetMapping("stream-chat")
    public ResponseEntity<Flux<String>> streamChat(@RequestParam(value="q",required = true) String query){

        return ResponseEntity.ok(this.chatService.streamChat(query));

    }
}
