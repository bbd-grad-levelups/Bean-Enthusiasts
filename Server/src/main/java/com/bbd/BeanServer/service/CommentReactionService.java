package com.bbd.BeanServer.service;

import com.bbd.shared.models.CommentReactions;

import com.bbd.BeanServer.repository.CommentReactionRepository;
import com.bbd.BeanServer.repository.CommentRepository;
import com.bbd.BeanServer.repository.ReactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentReactionService {
    @Autowired
    private CommentReactionRepository commentReactionRepository;

    @Autowired
    private ReactionRepository reactionRepository;

    @Autowired
    private CommentRepository commentRepository;

    public CommentReactions createCommentReaction(CommentReactions newCommentReaction) {
        return commentReactionRepository.save(newCommentReaction);
    }
}
