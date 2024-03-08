package com.bbd.BeanServer.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import org.springframework.web.bind.annotation.RestController;

import com.bbd.BeanServer.assembler.ModelAssembler;
import com.bbd.BeanServer.model.Greeting;
import com.bbd.BeanServer.model.Users;
import com.bbd.BeanServer.repository.UserRepository;
import com.bbd.BeanServer.service.GreetingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

@RestController
class TestController {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ModelAssembler<Greeting> greetingAssembler;
  @Autowired
  private ModelAssembler<Users> userAssembler;

  @Autowired
  private GreetingService greetingService;


  TestController() {

  }
  
  @GetMapping("/test")
  CollectionModel<EntityModel<Greeting>> all() {

    List<EntityModel<Greeting>> greetings = greetingService.getAllGreetings().stream()
      .map(greetingAssembler::toModel).collect(Collectors.toList());

    return CollectionModel.of(greetings);
  }

  @GetMapping("test/{id}")
  EntityModel<Greeting> getGreeting(@PathVariable int id) {
    Greeting greetingData = greetingService.getGreetingById(id);

    return greetingAssembler.toModel(greetingData);
  }
  
  @GetMapping("/user")
  CollectionModel<EntityModel<Users>> allUsers() {
    List <EntityModel<Users>> users = userRepository.findAll().stream()
    .map(userAssembler::toModel).collect(Collectors.toList());
    
    return CollectionModel.of(users);
  }

}