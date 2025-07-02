package com.ExpenseTracker.ExpenseTracker.controller;

import com.ExpenseTracker.ExpenseTracker.dto.AuthRequest;
import com.ExpenseTracker.ExpenseTracker.dto.RegisterRequest;
import com.ExpenseTracker.ExpenseTracker.security.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
public class SecuredEndpointTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtUtil jwtUtil;

    @Test
    public void testAccessWithoutToken() throws Exception {
        mockMvc.perform(get("/api/secure"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testAccessWithValidToken() throws Exception {
        // Register the user
        RegisterRequest request = new RegisterRequest("newUser78", "password");
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk());

        // Login to get a real token
        AuthRequest login = new AuthRequest("newUser78", "password");
        MvcResult result = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(login)))
                .andExpect(status().isOk())
                .andReturn();

        String responseJson = result.getResponse().getContentAsString();
        String token = JsonPath.read(responseJson, "$.token");

        // Access secured endpoint
        mockMvc.perform(get("/api/secure")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    public void testAccessWithExpiredToken() throws Exception {
        // Replace this with a real expired token during integration testing
        String expiredToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0VXNlciIsImV4cCI6MTYwOTU4NDAwMH0.VW_oFkBP38nBBp1MXa9CZSYRJzXfr1Y4n3GVg4w6vNo";
        mockMvc.perform(get("/api/secure")
                        .header("Authorization", "Bearer " + expiredToken))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testAccessWithMalformedToken() throws Exception {
        String token = "invalid.token.structure";
        mockMvc.perform(get("/api/secure")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());
    }
}
