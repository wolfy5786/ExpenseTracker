package com.ExpenseTracker.ExpenseTracker.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RegisterRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;

    public RegisterRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public RegisterRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

