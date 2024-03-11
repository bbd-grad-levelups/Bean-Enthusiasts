package com.bbd.BeanServer.service;

import java.util.Collections;
import java.util.List;

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
        List<FavoriteBean> FavoriteBean = repository.findAll();
        return FavoriteBean.isEmpty() ? Collections.emptyList() : FavoriteBean;
    }

}
