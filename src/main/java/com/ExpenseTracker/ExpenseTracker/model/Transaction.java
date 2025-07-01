package com.ExpenseTracker.ExpenseTracker.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Double amount;
    private Category category;
    private String description;

    public Transaction(Long transactionId, LocalDateTime createdAt, LocalDateTime updatedAt, Double amount, Category category, String description) {
        this.transactionId = transactionId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.amount = amount;
        this.category = category;
        this.description = description;
    }

    public Transaction() {
    }

    public Long getTransactionId() {
        return transactionId;
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
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id", nullable = false)
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
            return new Transaction(transactionId, createdAt, updatedAt, amount, category, description);
        }
    }
}
