package com.ExpenseTracker.ExpenseTracker.repository;

import com.ExpenseTracker.ExpenseTracker.model.Category;
import com.ExpenseTracker.ExpenseTracker.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByCategory(String category);
    List<Transaction> findByDateTimeBetween(LocalDateTime start, LocalDateTime end);
    List<Transaction> findTop1ByOrderByAmountDesc();
    List<Transaction> findTop1ByOrderByAmountAsc();


    //custom queries below are specific to SQL lite
    @Query(value = "SELECT * FROM transaction ORDER BY amount ASC LIMIT :n", nativeQuery = true)
    List<Transaction> findTopNByAmtAsc(@Param("n") int n);

    @Query(value = "SELECT * FROM transaction WHERE category = :cat ORDER BY amount ASC LIMIT :n", nativeQuery = true)
    List<Transaction> findTopNByAmtAsc(@Param("n")int n, @Param("cat")Category category);
}
