package com.bbd.BeanServer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bbd.shared.models.ReactionType;

public interface ReactionTypeRepository extends JpaRepository<ReactionType, Long> {
  
}
