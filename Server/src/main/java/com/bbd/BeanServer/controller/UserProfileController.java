package com.bbd.BeanServer.controller;

import com.bbd.BeanServer.service.CreateUserProfileService;
import com.bbd.BeanServer.service.UpdateUserProfileService;
import com.bbd.shared.assembler.ModelAssembler;
import com.bbd.shared.models.Post;
import com.bbd.shared.models.Users;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.oauth2.core.user.OAuth2User;

import java.net.URI;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@RestController
public class UserProfileController {

    @Autowired
    private CreateUserProfileService CreateUserProfileService;
    @Autowired
    private ModelAssembler<Post> postAssembler;

    @PostMapping("/createUserProfile")
    public ResponseEntity<Users> createUserProfile(@RequestBody Users newUser) {
        Users createdUser = CreateUserProfileService.createUserProfile(newUser);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdUser.getUser_id())
                .toUri();

        return ResponseEntity.created(location).body(createdUser);
    }

    @PostMapping("/updateUserProfile/Bio")
    public ResponseEntity<Users> updateUserProfileBio(@RequestBody Users updateUser) {
        // Return the response with the created post and location header
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(updateUser.getUser_id())
                .toUri();

        return ResponseEntity.created(location).body(updateUser);
    }
    
}
