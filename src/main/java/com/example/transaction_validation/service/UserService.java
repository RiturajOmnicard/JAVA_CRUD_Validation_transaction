package com.example.transaction_validation.service;

import com.example.transaction_validation.entity.User;

public interface UserService {
    User getOrCreateUser(String userId);
}
