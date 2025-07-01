package com.ExpenseTracker.ExpenseTracker.controller;

import com.ExpenseTracker.ExpenseTracker.security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SecuredEndpointTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtUtil jwtUtil;

    private final String SECURED_URL = "/api/transactions";

    @Test
    public void accessSecuredEndpointWithoutToken_shouldFail() throws Exception {
        mockMvc.perform(get(SECURED_URL))
                .andExpect(status().isForbidden()); // Or .isUnauthorized()
    }

    @Test
    public void accessSecuredEndpointWithValidToken_shouldPass() throws Exception {
        UserDetails userDetails = new User("testuser", "password123", Collections.emptyList());
        String token = jwtUtil.generateToken(userDetails);

        mockMvc.perform(get(SECURED_URL)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()); // Change based on expected behavior
    }
}
