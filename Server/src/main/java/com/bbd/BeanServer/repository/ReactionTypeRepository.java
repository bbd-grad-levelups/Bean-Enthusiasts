package com.bbd.BeanServer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bbd.shared.models.*;
import org.springframework.stereotype.Repository;

@Repository

public interface ReactionTypeRepository extends JpaRepository<ReactionType, Long> {

}
