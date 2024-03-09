package com.bbd.BeanServer.service;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bbd.BeanServer.repository.GreetingRepository;
import com.bbd.BeanServer.model.Greeting;

@Service
public class GreetingService {
  
  private final GreetingRepository repository;

  public GreetingService(GreetingRepository newRepository) {
    repository = newRepository;
  }

  public List<Greeting> getAllGreetings() {
    List<Greeting> greetings = repository.findAll();
    return greetings.isEmpty() ? Collections.emptyList() : greetings;
  }

  public Greeting getGreetingById(int id) {
    return repository.findById((long) id).orElse(new Greeting());
  }

}
