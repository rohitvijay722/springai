package com.example.springai.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.support.RestClientHttpServiceGroupConfigurer;
import org.springframework.web.service.registry.ImportHttpServices;

@Configuration
@ImportHttpServices(DummyPostClient.class)
public class DummyToolClientConfig {

    @Bean
    public RestClientHttpServiceGroupConfigurer dummyToolClientConfigurer(){
        return groups -> groups.forEachClient(
                (group, builder) -> builder.baseUrl("https://dummy-json.mock.beeceptor.com")
                .defaultHeader("Accept", "application/json"));
    }
}
