package com.bbd.BeanServer.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.Data;

@Entity
@Data
public class FavoriteBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favorite_bean_id")
    private int favoriteBeanId;

    @Column(name = "bean_name", length = 50, nullable = false)
    private String beanName;

    @Column(name = "is_banned", nullable = false)
    private boolean banned;

    public FavoriteBean() {
    }

    public FavoriteBean(String beanName, boolean banned) {
        this.beanName = beanName;
        this.banned = banned;
    }
}
