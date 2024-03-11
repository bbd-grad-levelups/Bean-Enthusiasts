package com.bbd.BeanServer.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbd.BeanServer.repository.PostRepository;
import com.bbd.shared.models.Post;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public Post createPost(Post newPost) {
        return postRepository.save(newPost);
    }
}
