package com.ExpenseTracker.ExpenseTracker.service;

import com.ExpenseTracker.ExpenseTracker.dto.AuthRequest;
import com.ExpenseTracker.ExpenseTracker.dto.RegisterRequest;
import com.ExpenseTracker.ExpenseTracker.exception.ResourceNotFoundException;
import com.ExpenseTracker.ExpenseTracker.mapper.UserMapper;
import com.ExpenseTracker.ExpenseTracker.model.User;
import com.ExpenseTracker.ExpenseTracker.repository.UserRepository;
import com.ExpenseTracker.ExpenseTracker.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private UserMapper userMapper;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       UserMapper userMapper,
                       UserDetailsService userDetailsService,
                       JwtUtil jwtUtil)
    {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    public void registerUser(RegisterRequest registerRequest)
    {
        userRepository.save(userMapper.newUser(registerRequest));
    }
    public boolean UserExistsUsername(String username)
    {
        return userRepository.findByUsername(username).isPresent();
    }
    public User findByUsername(String username)
    {
        return userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));
    }
    public String createToken(AuthRequest authRequest)
    {
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);
        return jwt;
    }



}
