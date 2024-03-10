package com.bbd.BeanServer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bbd.shared.models.*;

public interface UserRepository extends JpaRepository<Users, Long>{
  
}

