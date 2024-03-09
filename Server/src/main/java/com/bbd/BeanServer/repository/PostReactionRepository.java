package com.bbd.BeanServer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bbd.BeanServer.model.PostReaction;

public interface PostReactionRepository extends JpaRepository<PostReaction, Long>{
  
}
