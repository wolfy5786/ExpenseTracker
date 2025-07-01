package com.ExpenseTracker.ExpenseTracker.mapper;

import com.ExpenseTracker.ExpenseTracker.dto.UserDTO;
import com.ExpenseTracker.ExpenseTracker.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User newUser(UserDTO userDTO){
        return new User.Builder()
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .build();
    }

}
