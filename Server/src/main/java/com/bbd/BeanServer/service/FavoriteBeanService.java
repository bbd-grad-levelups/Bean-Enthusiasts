package com.bbd.BeanServer.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.bbd.BeanServer.repository.FavoriteBeanRepository;
import com.bbd.shared.models.FavoriteBean;

import lombok.NoArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class FavoriteBeanService {

    @Autowired
    private FavoriteBeanRepository repository;

    public List<FavoriteBean> getAllFavoriteBean() {
        List<FavoriteBean> beans = repository.findAll();
        return (beans != null && beans.isEmpty()) ? Collections.emptyList() : beans;
    }

    public Optional<FavoriteBean> getBeanByName(String name) {
        return repository.findAll().stream()
        .filter(x -> x.getBeanName().equalsIgnoreCase(name))
        .findFirst();
    }

    

}
