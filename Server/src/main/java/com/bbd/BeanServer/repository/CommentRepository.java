package com.bbd.BeanServer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bbd.shared.models.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
