package com.example.springai.config;

import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
public class VectorStoreConfig {

    @Bean
    public SimpleVectorStore vectorStore(EmbeddingModel embeddingModel) {
        SimpleVectorStore vectorStore = SimpleVectorStore.builder(embeddingModel).build();

        // 1. Create dummy data as Documents
        List<Document> dummyDocuments = List.of(
                new Document(
                        "Spring AI provides an abstract API to connect to multiple LLM providers.",
                        Map.of("category", "framework", "topic", "Spring AI", "importance", "high")
                ),
                new Document(
                        "InMemoryVectorStore is ideal for prototyping and testing but does not persist data.",
                        Map.of("category", "storage", "topic", "Vector Database")
                ),
                new Document(
                        "Retrieval-Augmented Generation (RAG) combines search with LLM generation.",
                        Map.of("category", "architecture", "topic", "RAG")
                ),
                new Document(
                        "The message chat memory advisor automatically handles conversation history.",
                        Map.of("category", "memory", "topic", "Advisors")
                ),
                new Document(
                "Rohit is a great software engineer, working in google gemini",
                Map.of("category", "enginner", "topic", "introduction")
        ),
                new Document(
                        "Rohit has a lot of money and knowledge, he an account number 12344321 and mobile number 434343",
                        Map.of("about", "human", "details", "personal")
                )
        );

        // 2. Add documents to the store (this automatically generates embeddings)
        vectorStore.add(dummyDocuments);

        return vectorStore;
    }

}
