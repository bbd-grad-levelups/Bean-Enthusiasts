package com.bbd.BeanServer;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bbd.BeanServer.db_classes.Users;

public interface UserRepository extends JpaRepository<Users, Long>{
  
}

