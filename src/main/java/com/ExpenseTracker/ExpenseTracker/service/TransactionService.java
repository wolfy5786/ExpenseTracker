package com.ExpenseTracker.ExpenseTracker.service;

import com.ExpenseTracker.ExpenseTracker.dto.TransactionDTO;
import com.ExpenseTracker.ExpenseTracker.model.Category;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionService {
    TransactionDTO createTransaction(String username, TransactionDTO dto);
    List<TransactionDTO> getAllTransactions(String username);
    List<TransactionDTO> getTransactionsByCategory(String username, Category category);
    List<TransactionDTO> getTransactionsBetweenDates(String username, LocalDateTime from, LocalDateTime to);
    List<TransactionDTO> getTransactionByCategoryBetweenDates(String username, Category category, LocalDateTime from, LocalDateTime to);
    List<TransactionDTO> getTopNTransactions(String username, int n, boolean ascending);
    TransactionDTO updateTransaction(String username, Long transactionId, TransactionDTO dto);
    void deleteTransaction(String username, Long transactionId);
}
