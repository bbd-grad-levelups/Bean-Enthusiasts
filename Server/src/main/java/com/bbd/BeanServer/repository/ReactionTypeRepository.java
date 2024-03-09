package com.bbd.BeanServer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bbd.BeanServer.model.ReactionType;

public interface ReactionTypeRepository extends JpaRepository<ReactionType, Long> {
  
}
