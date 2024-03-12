package com.bbd.BeanServer.service;


import com.bbd.shared.models.Reaction;

import com.bbd.BeanServer.repository.ReactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReactionService {

    @Autowired
    private ReactionRepository reactionRepository;

    public Reaction createReaction(Reaction newReaction) {
        return reactionRepository.save(newReaction);
    }
}
