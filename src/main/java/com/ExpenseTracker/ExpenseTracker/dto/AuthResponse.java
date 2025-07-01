package com.ExpenseTracker.ExpenseTracker.dto;

public class AuthResponse {
    private String jwt;
    private String username;

    public AuthResponse(String jwt, String username) {
        this.jwt = jwt;
        this.username = username;
    }

    public String getJwt() {
        return jwt;
    }

    public String getUsername() {
        return username;
    }
}

