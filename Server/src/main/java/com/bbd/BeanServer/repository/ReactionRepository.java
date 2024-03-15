package com.bbd.BeanServer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bbd.shared.models.*;
import com.bbd.shared.models.Reaction;
import org.springframework.stereotype.Repository;

@Repository

public interface ReactionRepository extends JpaRepository<Reaction, Long> {

}
