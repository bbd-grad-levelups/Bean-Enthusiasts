package com.bbd.BeanServer.service;

import com.bbd.BeanServer.repository.CommentRepository;
import com.bbd.shared.models.Comment;
import com.bbd.shared.models.Post;

import java.util.Optional;

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

    public Optional<Comment> getCommentById(int id) {
      return commentRepository.findAll().stream()
      .filter(x -> x.getComment_id()==id)
      .findFirst();
  }
}
