package com.bbd.BeanServer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bbd.BeanServer.model.FavoriteBean;

public interface FavoriteBeanRepository extends JpaRepository<FavoriteBean, Long>{

}

