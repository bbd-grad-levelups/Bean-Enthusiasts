package com.bbd.BeanServer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bbd.shared.models.Reaction;

public interface ReactionRepository extends JpaRepository<Reaction, Long>{

}
