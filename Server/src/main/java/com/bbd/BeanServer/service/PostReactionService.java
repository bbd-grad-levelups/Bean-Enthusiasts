package com.bbd.BeanServer.service;

import com.bbd.shared.models.PostReaction;

import com.bbd.BeanServer.repository.PostReactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostReactionService {
    @Autowired
    private PostReactionRepository postReactionRepository;


    @SuppressWarnings("null")
    public PostReaction createPostReaction(PostReaction newPostReaction) {
        return postReactionRepository.save(newPostReaction);
    }
}
