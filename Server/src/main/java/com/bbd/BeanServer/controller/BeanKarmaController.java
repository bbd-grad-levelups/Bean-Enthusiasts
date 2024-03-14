package com.bbd.BeanServer.controller;

import com.bbd.BeanServer.repository.*;
import com.bbd.shared.models.Comment;
import com.bbd.shared.models.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.bbd.BeanServer.repository.PostRepository;

import java.util.List;

@RestController
public class BeanKarmaController {


    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostReactionRepository postReactionRepository;

    @Autowired
    private CommentReactionRepository commentReactionRepository;

    @GetMapping("/calculatebeankarmaforuser/{user_id}")
    public int calculateBeanKarmaForUser(@PathVariable int user_id) {
        int beanKarma = 0;
        // Calculate bean karma for user's posts
        List<Post> userPosts = postRepository.findByUserId(user_id);
        for (Post post : userPosts) {
            beanKarma += postReactionRepository.getReactionCountForPost((long) post.getPostId());
        }

        // Calculate bean karma for user's comments
        List<Comment> userComments = commentRepository.findByUserId(user_id);
        for (Comment comment : userComments) {
            beanKarma += commentReactionRepository.getReactionCountForComment((long) comment.getComment_id());
        }

        return 0;
    }

    @GetMapping("/calculatebeankarmaforpost/{post_id}")
    public int calculateBeanKarmaForPost(@PathVariable int post_id) {
        int beanKarma = 0;
        beanKarma += postReactionRepository.getReactionCountForPost((long) post_id);
        return beanKarma;
    }

    @GetMapping("/calculatebeankarmaforcomment/{comment_id}")
    public int calculateBeanKarmaForComment(@PathVariable int comment_id) {
        int beanKarma = 0;
        beanKarma += commentReactionRepository.getReactionCountForComment((long) comment_id);
        return beanKarma;
    }
}
