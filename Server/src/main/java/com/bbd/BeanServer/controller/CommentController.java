package com.bbd.BeanServer.controller;


import com.bbd.BeanServer.service.CommentReactionService;
import com.bbd.BeanServer.service.ReactionService;
import com.bbd.shared.models.Reaction;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bbd.shared.models.CommentReaction;

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
        Reaction newReaction = mapper.convertValue(requestBody.get("reaction"), Reaction.class);
        reactionService.createReaction(newReaction);

        // only if reaction is successfully created then this entity should be created with FK of reactionid
        //TODO validation
        CommentReaction newCommentReaction = mapper.convertValue(requestBody.get("commentReaction"), CommentReaction.class);
        newCommentReaction.setReaction_id(newReaction.getReaction_id());
        commentReactionService.createCommentReaction(newCommentReaction);
        return ResponseEntity.ok().build();
    }

}
