package com.bbd.BeanServer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bbd.shared.models.CommentReaction;

public interface CommentReactionRepository extends JpaRepository<CommentReaction, Long> {

}
