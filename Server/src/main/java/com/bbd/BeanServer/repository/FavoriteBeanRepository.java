package com.bbd.BeanServer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bbd.shared.models.FavoriteBean;

public interface FavoriteBeanRepository extends JpaRepository<FavoriteBean, Long>{

}

