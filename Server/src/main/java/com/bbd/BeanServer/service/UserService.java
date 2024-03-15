package com.bbd.BeanServer.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbd.BeanServer.repository.TagRepository;
import com.bbd.BeanServer.repository.UserRepository;
import com.bbd.shared.models.Tag;
import com.bbd.shared.models.Users;

@Service
public class UserService {
  
  @Autowired
  private UserRepository repository;

  public Optional<Users> getUserByName(String name) {
      return repository.findAll().stream()
      .filter(x -> x.getUsername().equalsIgnoreCase(name))
      .findFirst();
  }

  
}
