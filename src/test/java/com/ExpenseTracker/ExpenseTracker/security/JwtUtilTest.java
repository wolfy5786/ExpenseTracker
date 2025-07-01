package com.ExpenseTracker.ExpenseTracker.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private final JwtUtil jwtUtil = new JwtUtil();

    @Test
    void testGenerateValidateExtractToken() {
        UserDetails userDetails = new User("testuser", "password", new ArrayList<>());

        String token = jwtUtil.generateToken(userDetails);

        assertNotNull(token);
        assertTrue(jwtUtil.validateToken(token, userDetails));
        assertEquals("testuser", jwtUtil.extractUsername(token));
    }
}
