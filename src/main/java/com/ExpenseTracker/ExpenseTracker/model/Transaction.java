package com.ExpenseTracker.ExpenseTracker.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transaction_id;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Double amount;
    private Category category;
    private String description;

    public Transaction(LocalDateTime createdAt, LocalDateTime updatedAt, Double amount, Category category, String description) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.amount = amount;
        this.category = category;
        this.description = description;
    }

    public Transaction() {
    }

    public Long getTransaction_id() {
        return transaction_id;
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
        private Long transaction_id;

        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private Double amount;
        private Category category;
        private String description;


    }
}
