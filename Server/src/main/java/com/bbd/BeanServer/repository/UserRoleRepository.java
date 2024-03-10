package com.bbd.BeanServer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bbd.shared.models.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
  
}
