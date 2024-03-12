package com.bbd.BeanServer.service;

import com.bbd.shared.models.PostReaction;

import com.bbd.BeanServer.repository.PostReactionRepository;
import com.bbd.BeanServer.repository.PostRepository;
import com.bbd.BeanServer.repository.ReactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostReactionService {
    @Autowired
    private PostReactionRepository postReactionRepository;

    @Autowired
    private ReactionRepository reactionRepository;

    @Autowired
    private PostRepository postRepository;

    public PostReaction createPostReaction(PostReaction newPostReaction) {
        return postReactionRepository.save(newPostReaction);
    }
}
