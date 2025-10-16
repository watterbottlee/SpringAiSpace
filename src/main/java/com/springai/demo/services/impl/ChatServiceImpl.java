package com.springai.demo.services.impl;

import com.springai.demo.services.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ChatServiceImpl implements ChatService {


    private ChatClient chatClient;

    @Value("classpath:/prompts/system-message.st")
    private Resource systemPrompt;

    @Value("classpath:/prompts/user-message.st")
    private Resource userPrompt;


    private VectorStore vectorStore;

    public ChatServiceImpl(ChatClient chatClient, VectorStore vectorStore){
        this.chatClient=chatClient;
        this.vectorStore=vectorStore;
    }

    @Override
    public String chat(String query, String userId) {

        log.info("User prompt exists: {}", userPrompt.exists());
        var result = chatClient.prompt()
                .advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID,userId))
                .system(systemPrompt)
                .user(user->user.text(this.userPrompt).param("query",query))
                .call()
                .content();
        return result;
        }

    @Override
    public Flux<String> streamChat(String query, String userId) {
        return this.chatClient.prompt()
                .advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID,userId))
                .system(systemPrompt)
                .user(user->user.text(this.userPrompt).param("query",query))
                .stream()
                .content();
    }

    @Override
    public void saveData(List<String> list) {

        List<Document> documentsList = list.stream().map(Document::new).toList();
        this.vectorStore.add(documentsList);
        System.out.println("returned data is saved successfully");

    }

}
