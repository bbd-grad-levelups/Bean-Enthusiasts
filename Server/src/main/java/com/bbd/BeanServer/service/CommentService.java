package com.bbd.BeanServer.service;

import com.bbd.BeanServer.repository.CommentRepository;
import com.bbd.shared.models.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @SuppressWarnings("null")
    public Comment createComment(Comment newComment) {
        return commentRepository.save(newComment);
    }
}
