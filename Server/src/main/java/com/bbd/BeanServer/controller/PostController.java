package com.bbd.BeanServer.controller;

import com.bbd.BeanServer.assembler.ModelAssembler;
import com.bbd.BeanServer.repository.PostRepository;
import com.bbd.BeanServer.service.PostService;
import com.bbd.shared.models.Post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@RestController
class PostController {

    @Autowired
    private PostRepository beansRepository;
    @Autowired
    private PostService postService;
    @Autowired
    private ModelAssembler<Post> postAssembler;

    @PostMapping("/createpost")
    public ResponseEntity<Post> createPost(@RequestBody Map<String, Object> postParams) {
        // Extract parameters from the request body
        int userId = Integer.parseInt((postParams.get("userId").toString()));
        int postId = Integer.parseInt((postParams.get("postId").toString()));
        String title = postParams.get("title").toString();
        String content = postParams.get("content").toString();

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

        Post newPost = new Post(userId, postId, title, content, currentTime);
        // Call the postService to create the post
        Post createdPost = postService.createPost(newPost);

        // Return the response with the created post and location header
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdPost.getPostId())
                .toUri();

        return ResponseEntity.created(location).body(createdPost);
    }


}


