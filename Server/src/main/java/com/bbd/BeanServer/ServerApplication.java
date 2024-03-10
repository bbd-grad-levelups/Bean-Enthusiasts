package com.bbd.BeanServer;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;


@SpringBootApplication
@EntityScan("com.bbd.shared.models")
public class ServerApplication {
  
  public static void main(String... args) {
    SpringApplication.run(ServerApplication.class, args);
  }
}
