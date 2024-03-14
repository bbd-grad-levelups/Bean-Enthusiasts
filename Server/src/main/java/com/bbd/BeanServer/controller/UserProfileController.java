package com.bbd.BeanServer.controller;

import com.bbd.BeanServer.service.CreateUserProfileService;
import com.bbd.shared.assembler.ModelAssembler;
import com.bbd.shared.models.Post;
import com.bbd.shared.models.Tag;
import com.bbd.shared.models.Users;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.net.URI;

@RestController
public class UserProfileController {

    @Autowired
    private CreateUserProfileService CreateUserProfileService;


    @PostMapping("/createUserProfile")
    public ResponseEntity<Users> createUserProfile(@RequestBody Users newUser) {
        Users createdUser = CreateUserProfileService.createUserProfile(newUser);
        // Return the response with the created post and location header
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdUser.getUser_id())
                .toUri();

        return ResponseEntity.created(location).body(createdUser);
    }

      @PostMapping("/user/find")
  ResponseEntity<?> returnSpecific(@RequestBody Users request) {
    if (request == null) {
      return ResponseEntity.badRequest().body("No value given");
    } else {
      return CreateUserProfileService.getUserByName(request.getUsername())
          .map(x -> {
            return ResponseEntity.ok(x);
          })
          .orElse(ResponseEntity.notFound().build());
    }
  }

}
