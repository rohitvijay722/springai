package com.example.springai.controller;

import com.example.springai.advisor.LoggingAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PromptController {

    // ChatModel is auto-configured and injected directly
//    private final ChatModel chatModel;

    // ChatClient is built using an auto-configured Builder
    private final ChatClient chatClient;

    public PromptController(ChatClient.Builder chatClientBuilder, ChatMemoryRepository chatMemoryRepository){
        // chatmemoryrepository uses concurrenthashmap to store the conversation id and content, we can use other type of chat memory respository as well
        this.chatClient = chatClientBuilder.defaultAdvisors(
                new LoggingAdvisor(),
                MessageChatMemoryAdvisor.builder(MessageWindowChatMemory.builder().chatMemoryRepository(chatMemoryRepository).maxMessages(2).build()).build()
        ).build();
    }

    @GetMapping("/prompt")
    public String getAnswer(@RequestParam String prompt, @RequestParam String conversationId){
        return chatClient.prompt(prompt).advisors(chatMemory -> chatMemory.param(MessageWindowChatMemory.CONVERSATION_ID, conversationId)).call().content();
    }

}
