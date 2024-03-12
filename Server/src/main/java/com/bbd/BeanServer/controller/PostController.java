package com.bbd.BeanServer.controller;

import com.bbd.BeanServer.repository.PostRepository;
import com.bbd.BeanServer.service.CommentReactionService;
import com.bbd.BeanServer.service.PostReactionService;
import com.bbd.BeanServer.service.PostService;
import com.bbd.BeanServer.service.ReactionService;
import com.bbd.shared.assembler.ModelAssembler;
import com.bbd.shared.models.PostReaction;
import com.bbd.shared.models.Post;

import com.bbd.shared.models.Reaction;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
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
    private PostReactionService postReactionService;
    @Autowired
    private ReactionService reactionService;
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

    @PostMapping("/postreaction")
    public ResponseEntity<Void> postReaction(@RequestBody Map<String, Object> requestBody) throws ChangeSetPersister.NotFoundException {
        ObjectMapper mapper = new ObjectMapper();

        // Deserialize Reaction object
        Reaction newReaction = mapper.convertValue(requestBody.get("reaction"), Reaction.class);
        reactionService.createReaction(newReaction);

        // only if reaction is successfully created then this entity should be created with FK of reactionid
        //TODO validation
        PostReaction newpostReaction = mapper.convertValue(requestBody.get("postReaction"), PostReaction.class);
        newpostReaction.setReaction_id(newReaction.getReaction_id());
        postReactionService.createPostReaction(newpostReaction);
        return ResponseEntity.ok().build();
    }


}


