package com.bbd.BeanServer.controller;


import com.bbd.BeanServer.service.CommentReactionService;
import com.bbd.BeanServer.service.ReactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bbd.shared.models.CommentReactions;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

@RestController
public class CommentController {
    @Autowired
    private CommentReactionService commentReactionService;

    @Autowired
    private ReactionService reactionService;


    @PostMapping("/commentreaction")
    public ResponseEntity<Void> commentReaction(@RequestBody Map<String, Object> requestBody) throws ChangeSetPersister.NotFoundException {
        ObjectMapper mapper = new ObjectMapper();

        // Deserialize Reaction object
//        Reaction newReaction = mapper.convertValue(requestBody.get("reaction"), Reaction.class);

        // Deserialize CommentReaction object
        CommentReactions newCommentReaction = mapper.convertValue(requestBody.get("commentReaction"), CommentReactions.class);
        System.out.println("\n \n received " + newCommentReaction);
//        reactionService.createReaction(newReaction);
        commentReactionService.createCommentReaction(newCommentReaction);
        return ResponseEntity.ok().build();
    }

}
