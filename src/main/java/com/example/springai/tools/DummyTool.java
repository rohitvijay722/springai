package com.example.springai.tools;

import com.example.springai.client.DummyPostClient;
import com.example.springai.dto.Post;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

/*
 * These are the tools that run in our own system we just need to use the annotation @Tool to configure any method as tool
 * I have provided these definition in defining chat client with tools.
 * For tools that are configured in other system/server the annotation is mcptool and that is use to create an mcp server which
 * any client can use.
 */
@Component
public class DummyTool {

    @Autowired
    private DummyPostClient dummyPostClient;

    @Tool(description = "Get the total blog post")
    String getTotalBlogPost(){
        ObjectMapper mapper = new ObjectMapper();
        String post = dummyPostClient.getPosts();
        List<Post> posts = mapper.readValue(post, new TypeReference<List<Post>>(){});// returning this did not work, need to check on what should be configured
        return post;

    }

    @Tool(description = "Get the total number of stars")
    int getTotalStars(int n){
        String str = "";
        for(int i=1 ; i<=n ;i++){
            str+="*";
            System.out.println(str);
        }
        return n*(n+1)/2;
    }

}
