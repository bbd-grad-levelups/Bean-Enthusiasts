package com.bbd.shared.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users {
  
  @Id 
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  int user_id;
  int user_role_id;
  int favorite_bean_id;
  String username;
  String bio;

  Users(int userRole, int favoriteBean, String username, String bio) {
    this.user_id = userRole;
    this.favorite_bean_id= favoriteBean;
    this.username = username;
    this.bio = bio;
  }
}
