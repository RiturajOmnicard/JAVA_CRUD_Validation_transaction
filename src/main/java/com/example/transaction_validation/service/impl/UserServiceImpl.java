package com.example.transaction_validation.service.impl;

import com.example.transaction_validation.entity.User;
import com.example.transaction_validation.repository.UserRepository;
import com.example.transaction_validation.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getOrCreateUser(String userId) {

        return userRepository.findById(userId).orElseGet(() -> {
            User newUser = new User();
            newUser.setUserId(userId);
            newUser.setStatus("ACTIVE");      // default status
            newUser.setBlacklisted(false);    // default blacklist
            return userRepository.save(newUser);
        });
    }
}
