package com.bbd.BeanServer.controller;

import com.bbd.BeanServer.assembler.ModelAssembler;
import com.bbd.BeanServer.model.FavoriteBean;
import com.bbd.BeanServer.repository.FavoriteBeanRepository;
import com.bbd.BeanServer.service.FavoriteBeanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import java.util.List;
import java.util.stream.Collectors;

@RestController
class BeansController {

    @Autowired
    private FavoriteBeanRepository beansRepository;
    @Autowired
    private FavoriteBeanService favoriteBeanService;

    @Autowired
    private ModelAssembler<FavoriteBean> beansAssembler;

    @GetMapping("/favoritebeans")
    CollectionModel<EntityModel<FavoriteBean>> all() {
        List<EntityModel<FavoriteBean>> greetings = favoriteBeanService.getAllFavoriteBeans().stream()
                .map(beansAssembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(greetings);
    }


}
