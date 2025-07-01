package com.ExpenseTracker.ExpenseTracker.dto;

public class UserDTO {
    private String username;
    private String password;

    public UserDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static class Builder{
        private String username;
        private String password;
        public Builder username(String username)
        {
            this.username = username;
            return this;
        }
        public Builder password(String password)
        {
            this.password = password;
            return this;
        }
        public UserDTO build()
        {
            return new UserDTO(username, password);
        }
    }
}
