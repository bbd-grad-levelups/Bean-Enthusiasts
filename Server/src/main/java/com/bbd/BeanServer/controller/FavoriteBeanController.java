package com.bbd.BeanServer.controller;

import com.bbd.BeanServer.repository.FavoriteBeanRepository;
import com.bbd.BeanServer.service.FavoriteBeanService;
import com.bbd.shared.models.FavoriteBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
class FavoriteBeanController {

    @Autowired
    private FavoriteBeanRepository repository;

    @Autowired 
    FavoriteBeanService service;

    @GetMapping("/favoritebean")
    ResponseEntity<?> returnAll() {
        List<FavoriteBean> beans = repository.findAll();
        return beans.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(beans);
    }

    @PostMapping("/favoritebean/find")
    ResponseEntity<?> returnSpecific(@RequestBody FavoriteBean request) {
        System.out.println("BOINK BOINK BOINKBOINKBOINK 9999");

        System.out.println(request);
        if (request == null) {
            System.out.println("BOINK BOINK BOINKBOINKBOINK");
            return ResponseEntity.badRequest().body("No value given");
        } else {
            System.out.println("BOINK");
            return service.getBeanByName(request.getBeanName())
            .map(matchedBean -> {
                return ResponseEntity.ok(matchedBean);
            })
            .orElse(ResponseEntity.notFound().build());
        }
    }

    @PostMapping("/favoritebean/add")
    ResponseEntity<?> addBean(@RequestBody FavoriteBean request) {
        System.out.println("\n\n\n\n\nReceived: " + request + "\n\n\n\n");
        if (request == null) {
            return ResponseEntity.badRequest().body("No value given");
        } else if (service.getBeanByName(request.getBeanName()).isPresent()) {
            return ResponseEntity.badRequest().body("The bean with name '" + request.getBeanName() + "' already exists.");
        } else {
            return ResponseEntity.ok(repository.save(request));
        }
    } 

    @PostMapping("/favoritebean/edit")
    ResponseEntity<?> changeBeanBanStatus(@RequestBody FavoriteBean request) {
        if (request == null) {
            return ResponseEntity.badRequest().body("No value given");
        } else {
            return service.getBeanByName(request.getBeanName())
            .map(editedBean -> {
                editedBean.setBanned(request.isBanned());
                editedBean = repository.save(editedBean);
                return ResponseEntity.ok(editedBean);
            })
            .orElse(ResponseEntity.notFound().build());
        }
    }

    @PostMapping("favoritebean/remove")
    ResponseEntity<?> removeBean(@RequestBody FavoriteBean request) {
        if (request == null) {
            return ResponseEntity.badRequest().body("No value given");
        } else {
            return service.getBeanByName(request.getBeanName())
            .map(matchedBean -> {
                repository.delete(matchedBean);
                return ResponseEntity.ok(matchedBean);
            }).orElse(ResponseEntity.notFound().build());
        }
    }

}
