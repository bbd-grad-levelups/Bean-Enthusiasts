package com.bbd.BeanServer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bbd.shared.models.Comments;

public interface CommentRepository extends JpaRepository<Comments, Long> {

}
