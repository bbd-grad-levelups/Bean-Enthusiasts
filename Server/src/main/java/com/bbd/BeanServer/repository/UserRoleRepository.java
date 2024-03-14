package com.bbd.BeanServer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bbd.shared.models.UserRole;
import org.springframework.stereotype.Repository;

@Repository

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

}
