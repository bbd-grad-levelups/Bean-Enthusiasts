package com.bbd.BeanServer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bbd.BeanServer.model.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {
  
}
