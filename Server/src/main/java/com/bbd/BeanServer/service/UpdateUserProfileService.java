package com.bbd.BeanServer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.bbd.BeanServer.repository.PostRepository;
import com.bbd.BeanServer.repository.UserRepository;
import com.bbd.shared.models.Post;
import com.bbd.shared.models.Users;

@Service
public class UpdateUserProfileService {

    @Autowired
    private UserRepository userRepository;

    public Users updateUserProfile(Users updateUser) {
        return userRepository.save(updateUser);
    }

}