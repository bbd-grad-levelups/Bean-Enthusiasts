package com.bbd.BeanServer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bbd.BeanServer.model.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
  
}
