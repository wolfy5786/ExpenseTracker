package com.ExpenseTracker.ExpenseTracker.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import io.jsonwebtoken.security.SignatureException;


import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class JwtUtilTest {

    @Autowired
    private JwtUtil jwtUtil;

    @Test
    public void testGenerateAndValidateToken() {
        UserDetails userDetails = User.builder()
                .username("testUser")
                .password("dummy")
                .roles("USER")
                .build();

        String token = jwtUtil.generateToken(userDetails);

        assertNotNull(token);
        assertEquals("testUser", jwtUtil.extractUsername(token));
        assertTrue(jwtUtil.validateToken(token, userDetails));
    }

    @Test
    public void testInvalidSignatureToken() {
        UserDetails userDetails = User.builder()
                .username("user")
                .password("dummy")
                .roles("USER")
                .build();

        String token = jwtUtil.generateToken(userDetails);
        String tamperedToken = token.substring(0, token.length() - 2) + "ab";

        assertThrows(SignatureException.class, () -> {
            jwtUtil.validateToken(tamperedToken, userDetails);
        });

    }

    @Test
    public void testWrongUserToken() {
        UserDetails userDetails1 = User.builder()
                .username("user1")
                .password("dummy")
                .roles("USER")
                .build();

        UserDetails userDetails2 = User.builder()
                .username("user2")
                .password("dummy")
                .roles("USER")
                .build();

        String token = jwtUtil.generateToken(userDetails1);
        assertFalse(jwtUtil.validateToken(token, userDetails2));
    }

    @BeforeEach
    public void setup() throws Exception {
        Field field = JwtUtil.class.getDeclaredField("secret");
        field.setAccessible(true);
        field.set(jwtUtil, "your-256-bit-secret-goes-here-1234567890123456");
    }

    @Test
    public void testExpiredToken() throws InterruptedException {
        UserDetails userDetails = User.builder()
                .username("tempUser")
                .password("dummy")
                .roles("USER")
                .build();

        String token = jwtUtil.generateToken(userDetails);
        Thread.sleep(2000); // Replace with mocked expiration in real cases
        assertDoesNotThrow(() -> jwtUtil.extractUsername(token));
    }
}
