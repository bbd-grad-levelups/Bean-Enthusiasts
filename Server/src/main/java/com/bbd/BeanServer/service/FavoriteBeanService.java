package com.bbd.BeanServer.service;

import java.util.Collections;
import java.util.List;

import com.bbd.BeanServer.repository.FavoriteBeanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbd.BeanServer.model.FavoriteBean;

@Service
public class FavoriteBeanService {

    @Autowired
    private FavoriteBeanRepository repository;

    public List<FavoriteBean> getAllFavoriteBean() {
        List<FavoriteBean> FavoriteBean = repository.findAll();
        return FavoriteBean.isEmpty() ? Collections.emptyList() : FavoriteBean;
    }

}
