package com.bbd.BeanServer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bbd.shared.models.CommentReactions;

public interface CommentReactionRepository extends JpaRepository<CommentReactions, Long> {

}
