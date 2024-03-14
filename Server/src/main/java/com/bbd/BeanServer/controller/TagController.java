package com.bbd.BeanServer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bbd.BeanServer.repository.TagRepository;
import com.bbd.BeanServer.service.TagService;
import com.bbd.shared.models.Tag;

import java.util.List;

@RestController
class TagController {

  @Autowired
  TagRepository repository;

  @Autowired
  TagService service;

  @GetMapping("/tag")
  ResponseEntity<?> returnAll() {
    List<Tag> tags = repository.findAll();
    return tags.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(tags);
  }

  @PostMapping("/tag/find")
  ResponseEntity<?> returnSpecific(@RequestBody Tag request) {
    if (request == null) {
      return ResponseEntity.badRequest().body("No value given");
    } else {
      return service.getTagByName(request.getTag_name())
          .map(x -> {
            return ResponseEntity.ok(x);
          })
          .orElse(ResponseEntity.notFound().build());
    }
  }

  @PostMapping("/tag/add")
  ResponseEntity<?> addEntity(@RequestBody Tag request) {
    if (request == null) {
      return ResponseEntity.badRequest().body("No value given");
    } else if (service.getTagByName(request.getTag_name()).isPresent()) {
      return ResponseEntity.badRequest().body("The tag with name '" + request.getTag_name() + "' already exists.");
    } else {
      return ResponseEntity.ok(repository.save(request));
    }
  }

  @PostMapping("/tag/edit")
  ResponseEntity<?> editEntity(@RequestBody Tag request) {
    if (request == null) {
      return ResponseEntity.badRequest().body("No value given");
    } else {
      return service.getTagByName(request.getTag_name())
          .map(x -> {
            x.setTag_name(request.getTag_name());
            x = repository.save(x);
            return ResponseEntity.ok(x);
          })
          .orElse(ResponseEntity.notFound().build());
    }
  }

  @PostMapping("tag/remove")
  ResponseEntity<?> removeEntity(@RequestBody Tag request) {
    if (request == null) {
      return ResponseEntity.badRequest().body("No value given");
    } else {
      return service.getTagByName(request.getTag_name())
      .map(x -> {
        repository.delete(x);
        return ResponseEntity.ok(x);
      }).orElse(ResponseEntity.notFound().build());
    }
  }

}
