package com.bbd.BeanServer.controller;

import com.bbd.BeanServer.assembler.ModelAssembler;
import com.bbd.BeanServer.model.Users;
import com.bbd.BeanServer.repository.UserRepository;
import com.bbd.BeanServer.service.CreateUserProfileService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import java.net.URI;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


@RestController
public class UserProfileController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelAssembler<Users> usersAssembler;

    @GetMapping("/createuserprofile")
    public ResponseEntity<Users> createNewUser(@RequestBody Map<String, Object> postParams){

    }

    
}
