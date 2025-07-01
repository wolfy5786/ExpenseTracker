package com.ExpenseTracker.ExpenseTracker.service;

import com.ExpenseTracker.ExpenseTracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class UserService {
    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

}
