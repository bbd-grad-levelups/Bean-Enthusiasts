package com.bbd.BeanServer.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbd.BeanServer.model.Post;
import com.bbd.BeanServer.repository.PostRepository;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public Post createPost(Post newPost) {
        return postRepository.save(newPost);
    }
}
