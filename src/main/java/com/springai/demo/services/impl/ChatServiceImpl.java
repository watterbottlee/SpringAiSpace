package com.springai.demo.services.impl;

import com.springai.demo.services.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@Slf4j
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatClient chatClient;

    @Value("classpath:/prompts/system-message.st")
    private Resource systemPrompt;

    @Value("classpath:/prompts/user-message.st")
    private Resource userPrompt;

    @Override
    public String chat(String query) {

        log.info("User prompt exists: {}", userPrompt.exists());
        var result = chatClient.prompt()
                .system(systemPrompt)
                .user(user->user.text(this.userPrompt).param("query",query))
                .call()
                .content();
        return result;
        }

    @Override
    public Flux<String> streamChat(String query) {
        return this.chatClient.prompt()
                .system(systemPrompt)
                .user(user->user.text(this.userPrompt).param("query",query))
                .stream()
                .content();
    }

}
