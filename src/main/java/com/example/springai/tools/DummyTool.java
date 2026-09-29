package com.example.springai.tools;

import com.example.springai.client.DummyPostClient;
import com.example.springai.dto.Post;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
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

    /**
     * For now, we will be sending the details like userId from tool param and in context,
     * we will be sending the details for account balance
     * @param userId the id of the user
     * @param context the context sent from client(chat client)
     * @return A string containing the product and its price, where product comes from user's shopping cart
     */
    @Tool(description = "Get the user shopping product details and price for amazon")
    String getShoppingCart(@ToolParam(description = "the id of the user") String userId, ToolContext context){
        String response = "product:";
        switch(userId){
            case "rvijay" -> {response+= "laptop, price:80";}
            case "vsoni" -> {response+= "pen, price:10";}
            case "pvaishanv" -> {response+= "cake, price:20";}
            case "ttiwari" -> {response+= "bottle, price:35";}
            default -> {response+= "hamburger, price:12";}
        }
        if(context.getContext().containsKey(userId) && (Integer)context.getContext().get(userId) >= Integer.parseInt(response.substring(response.length()-2))){
            return response;
        }
        return "Insufficient balance : " + context.getContext().get(userId);
    }

}
