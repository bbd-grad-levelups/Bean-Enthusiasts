package com.bbd.BeanServer.controller;

import com.bbd.BeanServer.repository.UserRepository;
import com.bbd.BeanServer.repository.UserRoleRepository;
import com.bbd.BeanServer.service.CreateUserProfileService;
import com.bbd.shared.assembler.ModelAssembler;
import com.bbd.shared.models.FavoriteBean;
import com.bbd.shared.models.Post;
import com.bbd.shared.models.Tag;
import com.bbd.shared.models.Users;

import jakarta.annotation.Nonnull;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.Optional;

@RestController
public class UserProfileController {

    @Autowired
    private CreateUserProfileService CreateUserProfileService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository roleRepository;
    


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

    @GetMapping("/user/find/{id}")
    ResponseEntity<?> returnSpecificUserbyID(@PathVariable int id) {
      return userRepository.findById((long) id).map(x -> {
        return ResponseEntity.ok(x);
      }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/role/find/{id}")
    ResponseEntity<?> returnSpecificRolebyID(@PathVariable int id) {
      return roleRepository.findById((long) id).map(x -> {
        return ResponseEntity.ok(x);
      }).orElse(ResponseEntity.notFound().build());
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

    @PostMapping("/user/setbean/{username}")
    ResponseEntity<?> setUserFavBean(@RequestBody FavoriteBean newBean, @PathVariable String username) {
      if (newBean == null) {
        return ResponseEntity.badRequest().body("No Value given");
      } else {
        Optional<Users> selectedUser = CreateUserProfileService.getUserByName(username);
        if (selectedUser.isPresent()) {
          @Nonnull Users user = selectedUser.get();
          user.setFavorite_bean_id(newBean.getFavoriteBeanId());
          userRepository.save(user);
          return ResponseEntity.ok(true);
        } else {
          return ResponseEntity.badRequest().body("User doesn't exist");
        }
      }
    }



}
