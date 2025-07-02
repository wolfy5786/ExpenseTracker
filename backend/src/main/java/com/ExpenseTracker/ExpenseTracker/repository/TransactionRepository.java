package com.ExpenseTracker.ExpenseTracker.repository;

import com.ExpenseTracker.ExpenseTracker.model.Category;
import com.ExpenseTracker.ExpenseTracker.model.Transaction;
import com.ExpenseTracker.ExpenseTracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List <Transaction> findByUser(User user);

    List<Transaction> findByUserAndCategory(User user, Category category);
    List<Transaction> findByUserAndCreatedAtBetween(User user, LocalDateTime start, LocalDateTime end);
    List<Transaction> findByUserAndCategoryAndCreatedAtBetween(User user, Category category, LocalDateTime start, LocalDateTime end);

    List<Transaction> findTop1ByUserOrderByAmountDesc(User user);
    List<Transaction> findTop1ByUserOrderByAmountAsc(User user);




    //custom queries below are specific to Postgres

    @Query(value = "SELECT * FROM transaction WHERE user_id = :userId ORDER BY amount ASC LIMIT :n", nativeQuery = true)
    List<Transaction> findTopNByAmtAsc(@Param("userId") Long userId, @Param("n") int n);

    @Query(value = "SELECT * FROM transaction WHERE user_id = :userId AND category = :cat ORDER BY amount ASC LIMIT :n", nativeQuery = true)
    List<Transaction> findTopNByAmtAsc(@Param("userId") Long userId, @Param("n") int n, @Param("cat") Category category);


    @Query(value = "SELECT * FROM transaction WHERE user_id = :userId ORDER BY amount DESC LIMIT :n", nativeQuery = true)
    List<Transaction> findTopNByAmtDesc(@Param("userId") Long userId, @Param("n") int n);

    @Query(value = "SELECT * FROM transaction WHERE user_id = :userId AND category = :cat ORDER BY amount DESC LIMIT :n", nativeQuery = true)
    List<Transaction> findTopNByAmtDesc(@Param("userId") Long userId, @Param("n") int n, @Param("cat") Category category);

    @Query(" SELECT t.category, SUM(t.amount) FROM Transaction t WHERE t.user.userId = :userId GROUP BY t.category ORDER BY SUM(t.amount) DESC")
    List<Object[]> findSpendingPerCategoryDesc(@Param("userId") Long userId);

    @Query("SELECT t.category, SUM(t.amount) FROM Transaction t WHERE t.user.userId = :userId GROUP BY t.category ORDER BY SUM(t.amount) ASC")
    List<Object[]> findSpendingPerCategoryAsc(@Param("userId") Long userId);


}
