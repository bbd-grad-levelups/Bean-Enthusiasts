package com.bbd.BeanServer.controller;


import com.bbd.BeanServer.service.PostReactionService;
import com.bbd.BeanServer.service.PostService;
import com.bbd.BeanServer.service.ReactionService;
import com.bbd.shared.models.Post;
import com.bbd.shared.models.PostReaction;
import com.bbd.shared.models.Reaction;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PostService postService;

    @MockBean
    private PostReactionService postReactionService;

    @MockBean
    private ReactionService reactionService;

    @Test
    public void testCreatePost() throws Exception {
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        Post newPost = new Post(4, 1, "My Post", "This is the content of my post", currentTime);


        // Mock the postService
        doNothing().when(postService).createPost(newPost);

        mockMvc.perform(MockMvcRequestBuilders.post("/createpost")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newPost)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testPostReaction() throws Exception {

        int userId = 0;
        int reactionTypeId = 2;
        int postId = 1;

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        Reaction newReaction = new Reaction(userId, reactionTypeId, currentTime);
        PostReaction newPostReaction = new PostReaction(postId, reactionTypeId);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("reaction", newReaction);
        requestBody.put("postReaction", newPostReaction);


        // Mock the reactionService
        doNothing().when(reactionService).createReaction(newReaction);

        mockMvc.perform(MockMvcRequestBuilders.post("/postreaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk());
    }
}
