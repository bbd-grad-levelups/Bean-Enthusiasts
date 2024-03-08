package com.bbd.BeanServer.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Users {
  
  @Id @GeneratedValue
  int user_id;
  int user_role_id;
  int favorite_bean_id;
  String username;
  String bio;

  Users() {

  }

  Users(int userRole, int favoriteBean, String username, String bio) {
    this.user_id = userRole;
    this.favorite_bean_id= favoriteBean;
    this.username = username;
    this.bio = bio;
  }
}
