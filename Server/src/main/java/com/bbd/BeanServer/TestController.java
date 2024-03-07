package com.bbd.BeanServer;

import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bbd.BeanServer.db_classes.Greeting;
import com.bbd.BeanServer.db_classes.Users;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

@RestController
class TestController {

  @Autowired
  private GreetingRepository greetingRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private ModelAssembler<Greeting> greetingAssembler;
  @Autowired
  private ModelAssembler<Users> userAssembler;
  TestController() {

  }


  
  @GetMapping("/test")
  CollectionModel<EntityModel<Greeting>> all() {

    List<EntityModel<Greeting>> greetings = greetingRepository.findAll().stream()
      .map(greetingAssembler::toModel).collect(Collectors.toList());

    return CollectionModel.of(greetings,
      linkTo(methodOn(TestController.class).all()).withSelfRel());
  }




  @GetMapping("test/{id}")
  EntityModel<Greeting> getGreeting(@PathVariable Long id) {
    Greeting greetingData = greetingRepository.findById(id).orElse(new Greeting("Nope", 1));

    return greetingAssembler.toModel(greetingData);
  }
  

  @GetMapping("/user")
  CollectionModel<EntityModel<Users>> allUsers() {
    List <EntityModel<Users>> users = userRepository.findAll().stream()
    .map(userAssembler::toModel).collect(Collectors.toList());
    
    return CollectionModel.of(users);
  }


}