package com.bbd.BeanServer.service;

import com.bbd.shared.models.CommentReaction;
import com.bbd.BeanServer.repository.CommentReactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentReactionService {
    @Autowired
    private CommentReactionRepository commentReactionRepository;

    public CommentReaction createCommentReaction(CommentReaction newCommentReaction) {
        return commentReactionRepository.save(newCommentReaction);
    }
}
