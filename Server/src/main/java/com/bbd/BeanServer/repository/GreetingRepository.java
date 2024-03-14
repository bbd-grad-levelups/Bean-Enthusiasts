package com.bbd.BeanServer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bbd.shared.models.Greeting;
import org.springframework.stereotype.Repository;

@Repository

public interface GreetingRepository extends JpaRepository<Greeting, Long> {

}

