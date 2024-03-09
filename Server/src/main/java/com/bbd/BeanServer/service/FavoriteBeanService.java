package com.bbd.BeanServer.service;

import java.util.Collections;
import java.util.List;

import com.bbd.BeanServer.repository.FavoriteBeanRepository;

import lombok.NoArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbd.BeanServer.model.FavoriteBean;

@Service
@NoArgsConstructor
public class FavoriteBeanService {

    @Autowired
    private FavoriteBeanRepository repository;

    public List<FavoriteBean> getAllFavoriteBeans() {
        List<FavoriteBean> FavoriteBeans = repository.findAll();
        return FavoriteBeans.isEmpty() ? Collections.emptyList() : FavoriteBeans;
    }

}
