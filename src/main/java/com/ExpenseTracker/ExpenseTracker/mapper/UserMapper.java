package com.ExpenseTracker.ExpenseTracker.mapper;

import com.ExpenseTracker.ExpenseTracker.dto.RegisterRequest;
import com.ExpenseTracker.ExpenseTracker.dto.UserDTO;
import com.ExpenseTracker.ExpenseTracker.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UserMapper(PasswordEncoder passwordEncoder)
    {
        this.passwordEncoder = passwordEncoder;
    }
    public User newUser(RegisterRequest registerRequest){
        return new User.Builder()
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .build();
    }

}
