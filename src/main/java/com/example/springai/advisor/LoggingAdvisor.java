package com.example.springai.advisor;

import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.stereotype.Component;

/*
 * The Spring AI Advisors API provides a flexible and powerful way to intercept,
 * modify, and enhance AI-driven interactions in your Spring applications.
 * By leveraging the Advisors API, developers can create more sophisticated,
 * reusable, and maintainable AI components.
 * Basically it is use to manipuate the request sent and response received from the LLM's and apart from that it supports
 * StreamAdvisor for reactive programming.
 */
@Component
public class LoggingAdvisor implements CallAdvisor{
    @Override
    public ChatClientResponse adviseCall(ChatClientRequest chatClientRequest, CallAdvisorChain callAdvisorChain) {
        System.out.println("Request :: " + chatClientRequest);
        ChatClientResponse chatClientResponse = callAdvisorChain.nextCall(chatClientRequest);
        System.out.println("Response :: " + chatClientResponse);
        return chatClientResponse;
    }

    @Override
    public String getName() {
        return "LoggingAdvisor";
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
