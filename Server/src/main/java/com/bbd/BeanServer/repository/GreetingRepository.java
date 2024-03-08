package com.bbd.BeanServer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bbd.BeanServer.model.Greeting;

public interface GreetingRepository extends JpaRepository<Greeting, Long>{
  
}

