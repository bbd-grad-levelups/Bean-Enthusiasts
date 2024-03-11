package com.bbd.BeanServer;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@EntityScan({"com.bbd.BeanServer", "com.bbd.shared"})
@ComponentScan({"com.bbd.BeanServer", "com.bbd.shared"})
public class ServerApplication {
  
  public static void main(String... args) {
    SpringApplication.run(ServerApplication.class, args);
  }
}
