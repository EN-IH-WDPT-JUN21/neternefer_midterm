package com.ironhack.Midterm.service;

import com.ironhack.Midterm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public boolean checkUserExists(Long id) {
        return userRepository.findById(id).isPresent();
    }
}

