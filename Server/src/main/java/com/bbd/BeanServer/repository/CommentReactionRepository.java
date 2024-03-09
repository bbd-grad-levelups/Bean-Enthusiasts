package com.bbd.BeanServer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bbd.BeanServer.model.CommentReaction;

public interface CommentReactionRepository extends JpaRepository<CommentReaction, Long> {
  
}
