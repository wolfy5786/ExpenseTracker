package com.ExpenseTracker.ExpenseTracker.dto;

import com.ExpenseTracker.ExpenseTracker.model.Category;

import java.time.LocalDateTime;

public class TransactionDTO {
    private Long transactionId;
    private LocalDateTime createdAt;    //we need time stamps at the front end for user
    private LocalDateTime updatedAt;
    private Double amount;
    private Category category;
    private String description;

    public TransactionDTO(LocalDateTime createdAt, LocalDateTime updatedAt, Double amount, Category category, String description) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.amount = amount;
        this.category = category;
        this.description = description;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
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
    public static class Builder{

        private Long transactionId;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private Double amount;
        private Category category;
        private String description;

        public Builder transactionId(Long transactionId)
        {
            this.transactionId = transactionId;
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
            this.amount = amount;
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
        public TransactionDTO build()
        {
            return new TransactionDTO(createdAt, updatedAt, amount, category, description);
        }
    }
}
