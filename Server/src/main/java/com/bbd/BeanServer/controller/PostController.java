package com.bbd.BeanServer.controller;

import com.bbd.BeanServer.repository.PostReactionRepository;
import com.bbd.BeanServer.repository.PostRepository;
import com.bbd.BeanServer.repository.UserRepository;
import com.bbd.BeanServer.service.CommentReactionService;
import com.bbd.BeanServer.service.PostReactionService;
import com.bbd.BeanServer.service.PostService;
import com.bbd.BeanServer.service.ReactionService;
import com.bbd.BeanServer.service.UserService;
import com.bbd.shared.assembler.ModelAssembler;
import com.bbd.shared.models.PostReaction;
import com.bbd.shared.models.Post;

import com.bbd.shared.models.Reaction;
import com.bbd.shared.models.Tag;
import com.bbd.shared.models.Users;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
class PostController {


    @Autowired
    private PostRepository beansRepository;

    @Autowired
    private PostReactionRepository postReactionRepository;
    @Autowired
    private PostService postService;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserService userService;
    @Autowired 
    private UserRepository userRepository;
    @Autowired
    private PostReactionService postReactionService;
    @Autowired
    private ReactionService reactionService;
    @Autowired
    private ModelAssembler<Post> postAssembler;

    @PostMapping("/createpost")
    public ResponseEntity<Post> createPost(@RequestBody Post newPost) {

        Post createdPost = postService.createPost(newPost);
        // Return the response with the created post and location header
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdPost.getPostId())
                .toUri();

        return ResponseEntity.created(location).body(createdPost);
    }

    @PostMapping("/postreaction")
    public ResponseEntity<Void> postReaction(@RequestBody PostReaction newPostReaction) {

        PostReaction createdPostReaction = postReactionRepository.save(newPostReaction);
        createdPostReaction.setReaction_id(newPostReaction.getReaction_id());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/findpost")
    ResponseEntity<?> returnSpecific(@RequestBody Post request) {
    if (request == null) {
      return ResponseEntity.badRequest().body("No value given");
    } else {
      return postService.getPostById(request.getPostId())
          .map(x -> {
            return ResponseEntity.ok(x);
          })
          .orElse(ResponseEntity.notFound().build());
    }
  }



    @PostMapping("/posts")
    ResponseEntity<?> returnAllPosts(@RequestBody String request) {
        return ResponseEntity.ok(postRepository.findAll());
    }

    @PostMapping("/posts/user")
    ResponseEntity<?> returnUserPosts(@RequestBody Users user) {
        Optional<Users> actualUser = userService.getUserByName(user.getUsername());

        if (actualUser.isPresent()) {
            long id = actualUser.get().getUser_id();

            List<Post> userPosts = postRepository.findAll().stream()
                    .filter(post -> post.getUserId() == id)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(userPosts);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/findpost")
    ResponseEntity<?> returnSpecific(@RequestBody Post request) {
    if (request == null) {
      return ResponseEntity.badRequest().body("No value given");
    } else {
      return postService.getPostById(request.getPostId())
          .map(x -> {
            return ResponseEntity.ok(x);
          })
          .orElse(ResponseEntity.notFound().build());
    }
  }




    @PostMapping("/posts/new")
    ResponseEntity<?> returnNewestPost(@RequestBody String mystring) {

        Optional<Post> newPost = postRepository.findAll().stream()
        .max(Comparator.comparing(Post::getDatePosted));

        if (newPost.isPresent()) {
            return ResponseEntity.ok(newPost.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/posts/find")
    ResponseEntity<?> returnSpecificPost(@RequestBody int postID) {
        Optional<Post> post =  postRepository.findById((long) postID);
        if (post.isPresent()) {
            return ResponseEntity.ok(post.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}







