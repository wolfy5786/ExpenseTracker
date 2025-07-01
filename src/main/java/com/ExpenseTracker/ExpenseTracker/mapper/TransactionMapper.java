package com.ExpenseTracker.ExpenseTracker.mapper;

import com.ExpenseTracker.ExpenseTracker.dto.TransactionDTO;
import com.ExpenseTracker.ExpenseTracker.model.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {
    public Transaction updateEntity(TransactionDTO transactionDTO, Transaction transaction)
    {
        return new Transaction.Builder()
                .transactionId(transaction.getTransactionId())
                .createdAt(transaction.getCreatedAt())
                .updatedAt(transactionDTO.getUpdatedAt())
                .amount(transactionDTO.getAmount())
                .category(transactionDTO.getCategory())
                .description(transactionDTO.getDescription())
                .build();
    }
    public Transaction newEntity(TransactionDTO transactionDTO)
    {
        return new Transaction.Builder()
                .createdAt(transactionDTO.getCreatedAt())
                .updatedAt(transactionDTO.getUpdatedAt())
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
