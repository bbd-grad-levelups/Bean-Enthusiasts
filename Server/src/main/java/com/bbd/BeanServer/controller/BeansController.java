package com.bbd.BeanServer.controller;


import com.bbd.BeanServer.assembler.ModelAssembler;
import com.bbd.BeanServer.model.FavoriteBean;
import com.bbd.BeanServer.repository.BeansRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/favouritebeans")
class BeansController {

    @Autowired
    private BeansRepository beansRepository;


    @Autowired
    private ModelAssembler<FavoriteBean> beansAssembler;

    @GetMapping
    public CollectionModel<EntityModel<FavoriteBean>> allBeans() {
        List<EntityModel<FavoriteBean>> beans = beansRepository.findAll().stream()
                .map(beansAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(beans);
    }


}
