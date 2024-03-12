package com.bbd.BeanServer.controller;

import com.bbd.BeanServer.repository.PostRepository;
import com.bbd.BeanServer.service.PostService;
import com.bbd.shared.assembler.ModelAssembler;
import com.bbd.shared.models.Post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.sql.Timestamp;
import java.util.Map;

@RestController
@SuppressWarnings("unused")
class PostController {

    
    @Autowired
    private PostRepository beansRepository;
    @Autowired
    private PostService postService;
    @Autowired
    private ModelAssembler<Post> postAssembler;

    @PostMapping("/createpost")
    public ResponseEntity<Post> createPost(@RequestBody Post newPost) {

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


