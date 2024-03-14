package com.bbd.BeanServer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bbd.shared.models.FavoriteBean;
import org.springframework.stereotype.Repository;

@Repository

public interface FavoriteBeanRepository extends JpaRepository<FavoriteBean, Long> {

}

