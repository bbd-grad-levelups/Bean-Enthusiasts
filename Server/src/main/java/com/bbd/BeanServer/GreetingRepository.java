package com.bbd.BeanServer;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bbd.BeanServer.db_classes.Greeting;

public interface GreetingRepository extends JpaRepository<Greeting, Long>{
  
}

