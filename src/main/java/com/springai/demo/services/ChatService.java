package com.springai.demo.services;

import com.springai.demo.entities.AiResponse;
import reactor.core.publisher.Flux;

import java.io.IOException;

public interface ChatService {

    String chat(String query, String userId) throws IOException;

    Flux<String> streamChat(String query, String userId);
}
