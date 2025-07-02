package com.ExpenseTracker.ExpenseTracker.controller;

import com.ExpenseTracker.ExpenseTracker.dto.AuthRequest;
import com.ExpenseTracker.ExpenseTracker.dto.AuthResponse;
import com.ExpenseTracker.ExpenseTracker.dto.RegisterRequest;
import com.ExpenseTracker.ExpenseTracker.dto.RegisterResponse;
import com.ExpenseTracker.ExpenseTracker.model.User;
import com.ExpenseTracker.ExpenseTracker.repository.UserRepository;
import com.ExpenseTracker.ExpenseTracker.security.JwtUtil;
import com.ExpenseTracker.ExpenseTracker.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }
    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(), authRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new RegisterResponse("Invalid username or password"));
        }

        final String jwt = userService.createToken(authRequest);

        User user = userService.findByUsername(authRequest.getUsername());

        return ResponseEntity.ok(new AuthResponse(jwt, user.getUsername()));
    }


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest request) {
        if (userService.UserExistsUsername(request.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new RegisterResponse("Username already exists"));
        }
        userService.registerUser(request);

        return ResponseEntity.ok(new RegisterResponse("User registered successfully"));
    }

}
