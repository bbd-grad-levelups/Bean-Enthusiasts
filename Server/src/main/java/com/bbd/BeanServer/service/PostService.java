package com.bbd.BeanServer.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbd.BeanServer.repository.PostRepository;
import com.bbd.shared.models.Post;
import com.bbd.shared.models.Tag;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public Post createPost(Post newPost) {
        return postRepository.save(newPost);
    }

     public Optional<Post> getPostById(int id) {
      return postRepository.findAll().stream()
      .filter(x -> x.getPostId()==id)
      .findFirst();
  }
}
