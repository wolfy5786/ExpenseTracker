package com.ExpenseTracker.ExpenseTracker.controller;

import com.ExpenseTracker.ExpenseTracker.dto.TransactionDTO;
import com.ExpenseTracker.ExpenseTracker.model.Category;
import com.ExpenseTracker.ExpenseTracker.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin(origins = "*") // Optional: for frontend integration
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public TransactionDTO createTransaction(
            @Valid @RequestBody TransactionDTO dto,
            Authentication authentication) {
        return transactionService.createTransaction(authentication.getName(), dto);
    }

    @GetMapping
    public List<TransactionDTO> getAllTransactions(Authentication authentication) {
        return transactionService.getAllTransactions(authentication.getName());
    }

    @GetMapping("/category")
    public List<TransactionDTO> getTransactionsByCategory(
            @RequestParam Category category,
            Authentication authentication) {
        return transactionService.getTransactionsByCategory(authentication.getName(), category);
    }


    @GetMapping("/range")
    public List<TransactionDTO> getTransactionsBetweenDates(
            @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,
            Authentication authentication) {
        return transactionService.getTransactionsBetweenDates(authentication.getName(), from, to);
    }

    @GetMapping("/category-range")
    public List<TransactionDTO> getTransactionsByCategoryBetweenDates(
            @RequestParam Category category,
            @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,
            Authentication authentication) {
        return transactionService.getTransactionByCategoryBetweenDates(authentication.getName(), category, from, to);
    }

    @GetMapping("/top")
    public List<TransactionDTO> getTopNTransactions(
            @RequestParam int n,
            @RequestParam(defaultValue = "false") boolean ascending,
            Authentication authentication) {
        return transactionService.getTopNTransactions(authentication.getName(), n, ascending);
    }

    @PutMapping("/{transactionId}")
    public TransactionDTO updateTransaction(
            @PathVariable Long transactionId,
            @Valid @RequestBody TransactionDTO dto,
            Authentication authentication) {
        return transactionService.updateTransaction(authentication.getName(), transactionId, dto);
    }

    @DeleteMapping("/{transactionId}")
    public void deleteTransaction(
            @PathVariable Long transactionId,
            Authentication authentication) {
        transactionService.deleteTransaction(authentication.getName(), transactionId);
    }
}

