package com.bbd.BeanServer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bbd.BeanServer.service.ReactionTypeService;
import com.bbd.shared.models.ReactionType;


@RestController
public class ReactionTypeController {

    @Autowired
    private ReactionTypeService ReactionTypeService;

    @PostMapping("/findreaction")
    ResponseEntity<?> returnSpecific(@RequestBody ReactionType request) {
  if (request == null) {
    return ResponseEntity.badRequest().body("No value given");
  } else {
    return ReactionTypeService.getReactionTypeById(request.getReaction_type_id())
        .map(x -> {
          return ResponseEntity.ok(x);
        })
        .orElse(ResponseEntity.notFound().build());
  }
}
}