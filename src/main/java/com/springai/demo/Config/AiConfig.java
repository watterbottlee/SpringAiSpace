package com.springai.demo.Config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.mistralai.MistralAiChatOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class AiConfig {

    private Logger logger = LoggerFactory.getLogger(AiConfig.class);

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder){

        return builder
                .defaultAdvisors(new SimpleLoggerAdvisor(),new SafeGuardAdvisor(List.of("games")))
                .defaultOptions(MistralAiChatOptions.builder()
                        .model("open-mixtral-8x7b")
                        .temperature(0.7)
                        .build())
                .build();
    }
}
