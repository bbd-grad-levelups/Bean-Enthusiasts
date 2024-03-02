package main.java.com.bbd.BeanServer;


import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;

@RestController
class TestController {

  TestController() {

  }

  @GetMapping("/test/1")
  ResponseEntity<?> hello() {
    String helloString = "Hello!";
    return ResponseEntity.ok().body(helloString);
  }

  @GetMapping("/test/2")
  ResponseEntity<?> world() {
    String helloString = "World!";
    return ResponseEntity.ok().body(helloString);
  }

  @GetMapping("/test/3")
  ResponseEntity<?> data() {
    String helloString = "This is a different string.";
    return ResponseEntity.ok().body(helloString);
  }
  

}