package com.bbd.BeanServer;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Greeting {
  
  @Id @GeneratedValue
  Long ID;
  String greeting;
  int randomVal;

  Greeting() {

  }

  Greeting(String newGreeting, int newVal) {
    this.greeting = newGreeting;
    this.randomVal = newVal;
  }
}
