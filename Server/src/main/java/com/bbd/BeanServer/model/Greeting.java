package com.bbd.BeanServer.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Greeting {
  
  @Id @GeneratedValue
  int ID;
  String greeting;
  int randomVal;

  public Greeting() {

  }

  public Greeting(String newGreeting, int newVal) {
    this.greeting = newGreeting;
    this.randomVal = newVal;
  }
}
