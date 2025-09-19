package com.springai.demo.services.impl;

import com.springai.demo.entities.AiResponse;
import com.springai.demo.services.ChatService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatClient chatClient;

    @Override
    public String chat(String query) {

        SystemPromptTemplate systemPromptTemplate = SystemPromptTemplate.builder()
                .template("you are a helpful coding assistant, answer like an expert ")
                .build();
        Message systemMessage = systemPromptTemplate.createMessage();
        PromptTemplate userPromtTemplate = PromptTemplate.builder()
                .template(query)
                .build();
        Message userMessage = userPromtTemplate.createMessage();

        Prompt prompt = new Prompt(systemMessage,userMessage);

        var result = chatClient.prompt(prompt)
                .call()
                .content();
        return result;
    }
}
