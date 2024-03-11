package com.bbd.BeanServer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbd.BeanServer.model.Users;
import com.bbd.BeanServer.repository.UserRepository;

@Service
public class CreateUserProfileService {
    @Autowired
    private UserRepository userRepository;

public Users createNewUser(Users newUser){
    return userRepository.save(newUser);
}
}
