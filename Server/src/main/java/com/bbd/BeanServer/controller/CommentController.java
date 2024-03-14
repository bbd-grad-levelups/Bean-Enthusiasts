package com.bbd.BeanServer.controller;


import com.bbd.BeanServer.repository.CommentRepository;
import com.bbd.BeanServer.service.CommentReactionService;
import com.bbd.BeanServer.service.CommentService;
import com.bbd.BeanServer.service.ReactionService;
import com.bbd.shared.models.Comment;
import com.bbd.shared.models.Reaction;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bbd.shared.models.CommentReaction;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class CommentController {
    @Autowired
    private CommentReactionService commentReactionService;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ReactionService reactionService;

    @Autowired
    private CommentService commentService;

    @PostMapping("/commentreaction")
    public ResponseEntity<Void> commentReaction(@RequestBody Map<String, Object> requestBody) throws ChangeSetPersister.NotFoundException {
        ObjectMapper mapper = new ObjectMapper();

        // Deserialize Reaction object
        Reaction newReaction = mapper.convertValue(requestBody.get("reaction"), Reaction.class);
        reactionService.createReaction(newReaction);

        // only if reaction is successfully created then this entity should be created with FK of reactionid
        //TODO validation
        CommentReaction newCommentReaction = mapper.convertValue(requestBody.get("commentReaction"), CommentReaction.class);
        newCommentReaction.setReaction_id(newReaction.getReaction_id());
        commentReactionService.createCommentReaction(newCommentReaction);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/createcomment")
    public ResponseEntity<Comment> createComment(@RequestBody Comment newComment) {
        Comment createdComment = commentService.createCommentReaction(newComment);
        // Return the response with the created comment and location header
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdComment.getComment_id())
                .toUri();

        return ResponseEntity.created(location).body(createdComment);
    }

    @PostMapping("/post/comment")
    public ResponseEntity<?> getPostComments(@RequestBody int postID) {
        List<Comment> postComments = commentRepository.findAll().stream()
        .filter(x -> (x.getPost_id() == postID)).collect(Collectors.toList());
        return ResponseEntity.ok(postComments);
    }

}
