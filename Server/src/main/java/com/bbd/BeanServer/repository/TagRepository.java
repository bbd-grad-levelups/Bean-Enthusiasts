package com.bbd.BeanServer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bbd.shared.models.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {
  
}
