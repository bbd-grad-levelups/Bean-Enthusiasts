package com.bbd.BeanServer.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bbd.shared.assembler.ModelAssembler;
import com.bbd.shared.models.*;
import com.bbd.BeanServer.repository.FavoriteBeanRepository;
import com.bbd.BeanServer.repository.UserRepository;
import com.bbd.BeanServer.service.GreetingService;
import com.bbd.shared.models.FavoriteBean;
import com.bbd.shared.models.Greeting;
import com.bbd.shared.request_model.BanBeanRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

@RestController
class TestController {

  @Autowired
  private UserRepository userRepository;
  
  @Autowired 
  FavoriteBeanRepository beanRepository;

  @Autowired
  private ModelAssembler<Greeting> greetingAssembler;
  @Autowired
  private ModelAssembler<Users> userAssembler;

  @Autowired
  private GreetingService greetingService;


  TestController() {

  }
  
  @SuppressWarnings("null")
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
  
  @SuppressWarnings("null")
  @GetMapping("/test/user")
  CollectionModel<EntityModel<Users>> allUsers() {
    List <EntityModel<Users>> users = userRepository.findAll().stream()
    .map(userAssembler::toModel).collect(Collectors.toList());
    
    return CollectionModel.of(users);
  }

  @PostMapping("/favoritebean/ban")
  ResponseEntity<FavoriteBean> changeBeanBanStatus(@RequestBody BanBeanRequest request) {
    
    int beanID = request.getBean_id();
    return beanRepository.findById((long) beanID)
    .map(editedBean -> {
      editedBean.setBanned(request.is_banned());
      editedBean = beanRepository.save(editedBean);
      return ResponseEntity.ok(editedBean);
    })
    .orElse(ResponseEntity.notFound().build());
    
  }

}