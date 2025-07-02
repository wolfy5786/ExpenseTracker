package com.ExpenseTracker.ExpenseTracker.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users",
        indexes = {
                @Index(name = "idx_userId", columnList = "userId"),
                @Index(name = "idx_username", columnList = "username")}
)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column(name = "username",unique = true)
    private String username;
    @Column(name = "password")
    private String password;


    public User(Long userId, String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
    }

    public User() {
    }

    public Long getId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    public static class Builder {
        private Long userId;
        private String username;
        private String password;
        public Builder userId(Long userId)
        {
            this.userId = userId;
            return this;
        }
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
        public User build()
        {
            return new User(userId, username, password);
        }
    }
}
