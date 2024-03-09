package com.bbd.BeanServer.service;

import java.util.Collections;
import java.util.List;

import com.bbd.BeanServer.repository.FavoriteBeanRepository;
import org.springframework.stereotype.Service;

import com.bbd.BeanServer.model.FavoriteBean;

@Service
public class FavoriteBeanService {

    private final FavoriteBeanRepository repository;

    public FavoriteBeanService(FavoriteBeanRepository newRepository) {
        repository = newRepository;
    }

    public List<FavoriteBean> getAllFavoriteBeans() {
        List<FavoriteBean> FavoriteBeans = repository.findAll();
        return FavoriteBeans.isEmpty() ? Collections.emptyList() : FavoriteBeans;
    }

}
