package com.example.springai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PromptController {

    // ChatModel is auto-configured and injected directly
//    private final ChatModel chatModel;

    // ChatClient is built using an auto-configured Builder
    private final ChatClient chatClient;

    public PromptController(ChatClient.Builder chatClientBuilder){
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping("/prompt")
    public String getAnswer(@RequestParam String prompt){
        return chatClient.prompt(prompt).call().content();
    }

}
