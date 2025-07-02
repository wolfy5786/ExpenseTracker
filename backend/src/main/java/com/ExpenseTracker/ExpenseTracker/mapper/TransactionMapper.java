package com.ExpenseTracker.ExpenseTracker.mapper;

import com.ExpenseTracker.ExpenseTracker.dto.TransactionDTO;
import com.ExpenseTracker.ExpenseTracker.model.Transaction;
import com.ExpenseTracker.ExpenseTracker.model.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TransactionMapper {
    public Transaction updateEntity(TransactionDTO transactionDTO, Transaction transaction)
    {
        return new Transaction.Builder()
                .transactionId(transaction.getTransactionId())
                .user(transaction.getUser())
                .createdAt(transaction.getCreatedAt()) //from inital transaction, should not be modified
                .updatedAt(LocalDateTime.now()) //update at modify
                .amount(transactionDTO.getAmount())
                .category(transactionDTO.getCategory())
                .description(transactionDTO.getDescription())
                .build();
    }
    public Transaction newEntity(TransactionDTO transactionDTO, User user)
    {
        return new Transaction.Builder()
                .user(user)
                .createdAt(LocalDateTime.now()) //update now
                .updatedAt(LocalDateTime.now()) //update now
                .description(transactionDTO.getDescription())
                .amount(transactionDTO.getAmount())
                .category(transactionDTO.getCategory())
                .build();
    }
    public TransactionDTO newDto(Transaction transaction)
    {
        return new TransactionDTO.Builder()
                .transactionId(transaction.getTransactionId())
                .amount(transaction.getAmount())
                .category(transaction.getCategory())
                .description(transaction.getDescription())
                .createdAt(transaction.getCreatedAt())
                .updatedAt(transaction.getUpdatedAt())
                .build();
    }
}
