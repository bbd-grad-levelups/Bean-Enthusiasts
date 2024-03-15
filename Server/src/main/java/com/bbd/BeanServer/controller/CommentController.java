package com.bbd.BeanServer.controller;


import com.bbd.BeanServer.repository.CommentReactionRepository;
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
import com.bbd.shared.models.Post;
import com.bbd.shared.models.PostReaction;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@RestController
public class CommentController {
    @Autowired
    private CommentReactionService commentReactionService;

    @Autowired
    private ReactionService reactionService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentReactionRepository commentReactionRepository;

     @PostMapping("/commentreaction")
    public ResponseEntity<Void> CommentReaction(@RequestBody CommentReaction newCommentReaction) {

        CommentReaction createdCommentReaction = commentReactionRepository.save(newCommentReaction);
        createdCommentReaction.setReaction_id(newCommentReaction.getReaction_id());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/createcomment")
    public ResponseEntity<Comment> createComment(@RequestBody Comment newComment) {
        
        Comment createdComment = commentService.createComment(newComment);
        // Return the response with the created comment and location header
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdComment.getComment_id())
                .toUri();

        return ResponseEntity.created(location).body(createdComment);
    }

      @PostMapping("/findcomment")
    ResponseEntity<?> returnSpecific(@RequestBody Comment request) {
    if (request == null) {
      return ResponseEntity.badRequest().body("No value given");
    } else {
      return commentService.getCommentById(request.getComment_id())
          .map(x -> {
            return ResponseEntity.ok(x);
          })
          .orElse(ResponseEntity.notFound().build());
    }
  }

}
