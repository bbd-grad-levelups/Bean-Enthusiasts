package com.bbd.BeanServer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bbd.shared.models.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

}

