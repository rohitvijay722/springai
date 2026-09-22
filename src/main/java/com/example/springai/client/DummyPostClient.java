package com.example.springai.client;

import com.example.springai.dto.Post;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;

@HttpExchange // not mandatory
public interface DummyPostClient {

    @GetExchange(value = "/posts", accept = "application/json")
    String getPosts();
}
