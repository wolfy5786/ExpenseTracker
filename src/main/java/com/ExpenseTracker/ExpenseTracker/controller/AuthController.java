package com.ExpenseTracker.ExpenseTracker.controller;

import com.ExpenseTracker.ExpenseTracker.dto.AuthRequest;
import com.ExpenseTracker.ExpenseTracker.dto.AuthResponse;
import com.ExpenseTracker.ExpenseTracker.dto.RegisterRequest;
import com.ExpenseTracker.ExpenseTracker.dto.RegisterResponse;
import com.ExpenseTracker.ExpenseTracker.model.User;
import com.ExpenseTracker.ExpenseTracker.repository.UserRepository;
import com.ExpenseTracker.ExpenseTracker.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(
            AuthenticationManager authenticationManager,
            UserDetailsService userDetailsService,
            JwtUtil jwtUtil,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @PostMapping("/login")
    public AuthResponse createAuthenticationToken(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(), authRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new RuntimeException("Invalid username or password", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);
        User user = userRepository.findByUsername(authRequest.getUsername()).orElseThrow();

        return new AuthResponse(jwt, user.getUsername());
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new RegisterResponse("Username already exists"));
        }

        User newUser = new User.Builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(newUser);
        return ResponseEntity.ok(new RegisterResponse("User registered successfully"));
    }

}
