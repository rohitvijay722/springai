package com.example.springai.controller;

import com.example.springai.advisor.LoggingAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.VectorStoreRetriever;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
public class PromptController {

    // ChatModel is auto-configured and injected directly
//    private final ChatModel chatModel;

    // ChatClient is built using an auto-configured Builder
    private final ChatClient chatClient;
    private final VectorStoreRetriever vectorStoreRetriever;

    public PromptController(ChatClient.Builder chatClientBuilder, ChatMemoryRepository chatMemoryRepository, VectorStoreRetriever vectorStoreRetriever){
        // chatmemoryrepository uses concurrenthashmap to store the conversation id and content, we can use other type of chat memory respository as well
        this.chatClient = chatClientBuilder.defaultAdvisors(
                new LoggingAdvisor(),
                MessageChatMemoryAdvisor.builder(MessageWindowChatMemory.builder().chatMemoryRepository(chatMemoryRepository).maxMessages(3).build()).build()
        ).build();
        this.vectorStoreRetriever = vectorStoreRetriever;
    }

    @GetMapping("/prompt")
    public String getAnswer(@RequestParam String prompt, @RequestParam String conversationId){
        // adding context from simple vector store using vectorstoreretriever
        String context = vectorStoreRetriever.similaritySearch(SearchRequest.builder().query(prompt).similarityThreshold(0.6d).topK(1).build()).stream().map(Document::getText).collect(Collectors.joining("\n", "[", "]"));

//        PromptTemplate promptTemplate = new PromptTemplate("""
//                Context: ${context}
//
//                Question: ${prompt}
//                """);
//        promptTemplate.add("context", context);
//        promptTemplate.add("prompt", prompt);
//        Prompt finalPrompt = promptTemplate.create();

        return chatClient.prompt().user(p -> p.text("""
                Context: ${context}
                
                Question: ${prompt}
                """).param("context", context).param("prompt", prompt))
                .advisors(chatMemory -> chatMemory.param(MessageWindowChatMemory.CONVERSATION_ID, conversationId))
                .call()
                .content();
    }

}
