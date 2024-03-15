package com.bbd.BeanServer.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.bbd.BeanServer.repository.PostRepository;
import com.bbd.BeanServer.repository.ReactionRepository;
import com.bbd.BeanServer.service.ReactionService;
import com.bbd.shared.models.Reaction;



@RestController
public class ReactionController {

      @Autowired
    private ReactionRepository reactionRepository;

    @Autowired
    private ReactionService reactionService;

    @SuppressWarnings("null")
    @PostMapping("/reaction")
    public ResponseEntity<Reaction> reaction(@RequestBody Reaction newReaction) {

        Reaction createdReaction = reactionRepository.save(newReaction);

        //Reaction createdReaction = reactionService.createReaction(newReaction);
        createdReaction.setReaction_id(newReaction.getReaction_id());
                URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdReaction.getReaction_id())
                .toUri();
        return ResponseEntity.created(location).body(createdReaction);
    }
    
}
