package com.bbd.BeanServer.controller;

import com.bbd.BeanServer.service.FavoriteBeanService;
import com.bbd.shared.assembler.ModelAssembler;
import com.bbd.shared.models.FavoriteBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import java.util.List;
import java.util.stream.Collectors;

@RestController
class FavoriteBeanController {

    @Autowired
    private FavoriteBeanService favoriteBeanService;

    @Autowired
    private ModelAssembler<FavoriteBean> beansAssembler;

    @SuppressWarnings("null")
    @GetMapping("/favoritebeans")
    CollectionModel<EntityModel<FavoriteBean>> all() {
        List<EntityModel<FavoriteBean>> greetings = favoriteBeanService.getAllFavoriteBean().stream()
                .map(beansAssembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(greetings);
    }


}
