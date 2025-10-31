package com.springai.demo.services.impl;

import com.springai.demo.services.ChatService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.generation.augmentation.ContextualQueryAugmenter;
import org.springframework.ai.rag.preretrieval.query.expansion.MultiQueryExpander;
import org.springframework.ai.rag.preretrieval.query.transformation.RewriteQueryTransformer;
import org.springframework.ai.rag.preretrieval.query.transformation.TranslationQueryTransformer;
import org.springframework.ai.rag.retrieval.join.ConcatenationDocumentJoiner;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import org.slf4j.Logger; // Change this import
import org.slf4j.LoggerFactory;

@Service
public class ChatServiceImpl implements ChatService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

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
    public String getResponse(String userQuery){
        var advisor = RetrievalAugmentationAdvisor.builder()
                .queryTransformers(
                        RewriteQueryTransformer.builder()
                                .chatClientBuilder(chatClient.mutate().clone())
                                .build(),
                        TranslationQueryTransformer.builder()
                                .chatClientBuilder(chatClient.mutate().clone()).targetLanguage("english")
                                .build()
                )
                .queryExpander(MultiQueryExpander
                        .builder()
                        .chatClientBuilder(chatClient.mutate().clone())
                        .build())
                .documentRetriever(VectorStoreDocumentRetriever.builder()
                        .vectorStore(vectorStore)
                        .topK(4)
                        .similarityThreshold(0.3)
                        .build()
                )
                .documentJoiner(new ConcatenationDocumentJoiner())
                .queryAugmenter(ContextualQueryAugmenter.builder().build())
                .build();
        return chatClient
                .prompt()
                .advisors(advisor)
                .user(userQuery)
                .call()
                .content();
    }

    @Override
    public void saveData(List<String> list) {
        List<Document> documentsList = list.stream().map(Document::new).toList();
        this.vectorStore.add(documentsList);
    }

}
