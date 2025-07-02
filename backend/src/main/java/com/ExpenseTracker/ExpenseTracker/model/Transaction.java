package com.ExpenseTracker.ExpenseTracker.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "transaction",indexes = {
        @Index(name = "idx_transactionId", columnList = "transactionId"),
        @Index(name = "idx_category", columnList = "category")}
)

public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Double amount;
    @Enumerated(EnumType.STRING)
    private Category category;
    private String description;

    public Transaction(Long transactionId, LocalDateTime createdAt, LocalDateTime updatedAt,User user, Double amount, Category category, String description) {
        this.transactionId = transactionId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.user = user;
        this.amount = amount;
        this.category = category;
        this.description = description;
    }

    public Transaction() {
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public User getUser() {
        return user;
    }

    public Double getAmount() {
        return amount;
    }

    public Category getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public static class Builder{
        private Long transactionId;
        private User user;

        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private Double amount;
        private Category category;
        private String description;

        public Builder transactionId(Long transactionId)
        {
            this.transactionId = transactionId;
            return  this;
        }
        public Builder user(User user)
        {
            this.user = user;
            return this;
        }
        public Builder createdAt(LocalDateTime createdAt)
        {
            this.createdAt = createdAt;
            return this;
        }
        public Builder updatedAt(LocalDateTime updatedAt)
        {
            this.updatedAt = updatedAt;
            return this;
        }
        public Builder amount(Double amount)
        {
            this.amount =amount;
            return this;
        }
        public Builder category(Category category)
        {
            this.category = category;
            return this;
        }
        public Builder description(String description)
        {
            this.description = description;
            return this;
        }
        public Transaction build ()
        {
            return new Transaction(transactionId, createdAt, updatedAt, user , amount, category, description);
        }
    }
}
