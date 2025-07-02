package com.ExpenseTracker.ExpenseTracker.config;

import com.ExpenseTracker.ExpenseTracker.dto.RegisterRequest;
import com.ExpenseTracker.ExpenseTracker.mapper.UserMapper;
import com.ExpenseTracker.ExpenseTracker.model.Category;
import com.ExpenseTracker.ExpenseTracker.model.Transaction;
import com.ExpenseTracker.ExpenseTracker.model.User;
import com.ExpenseTracker.ExpenseTracker.repository.TransactionRepository;
import com.ExpenseTracker.ExpenseTracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class MockDataLoader {

    @Autowired
    UserMapper userMapper;

    @Bean
    public CommandLineRunner loadMockData(UserRepository userRepo, TransactionRepository transactionRepo) {
        return args -> {
            if (userRepo.count() == 0) {
                System.out.println("Seeding mock data...");

                // Create user
                    User mockUser = userMapper.newUser(new RegisterRequest("cake", "chef"));
                mockUser = userRepo.save(mockUser);

                // Create transactions
                Transaction t1 = new Transaction.Builder().createdAt(LocalDateTime.now().minusDays(10)).updatedAt(LocalDateTime.now()).description("Carrots").user(mockUser).amount(500.25).category(Category.GROCERIES).build();
                Transaction t2 = new Transaction.Builder().createdAt(LocalDateTime.now().minusDays(10)).updatedAt(LocalDateTime.now()).description("medicine").user(mockUser).amount(50.5).category(Category.HEALTH).build();
                Transaction t3 = new Transaction.Builder().createdAt(LocalDateTime.now().minusDays(10)).updatedAt(LocalDateTime.now()).description("Onions").user(mockUser).amount(241.75).category(Category.GROCERIES).build();
                Transaction t4 = new Transaction.Builder().createdAt(LocalDateTime.now().minusDays(10)).updatedAt(LocalDateTime.now()).description("rent").user(mockUser).amount(1250.00).category(Category.RENT).build();
                Transaction t5 = new Transaction.Builder().createdAt(LocalDateTime.now().minusDays(10)).updatedAt(LocalDateTime.now()).description("soap").user(mockUser).amount(10.25).category(Category.CLEANING).build();
                Transaction t6 = new Transaction.Builder().createdAt(LocalDateTime.now().minusDays(10)).updatedAt(LocalDateTime.now()).description("drinks").user(mockUser).amount(500.25).category(Category.MISCELLANEOUS).build();
                Transaction t7 = new Transaction.Builder().createdAt(LocalDateTime.now().minusDays(10)).updatedAt(LocalDateTime.now()).description("water bill").user(mockUser).amount(750.25).category(Category.UTILITIES).build();
                Transaction t8 =new Transaction.Builder().createdAt(LocalDateTime.now().minusDays(10)).updatedAt(LocalDateTime.now()).description("dinner").user(mockUser).amount(222.222).category(Category.RESTAURANTS).build();
                Transaction t9 = new Transaction.Builder().createdAt(LocalDateTime.now().minusDays(10)).updatedAt(LocalDateTime.now()).description("gas bill").user(mockUser).amount(376.13).category(Category.UTILITIES).build();
                Transaction t10 = new Transaction.Builder().createdAt(LocalDateTime.now().minusDays(10)).updatedAt(LocalDateTime.now()).description("streamflix").user(mockUser).amount(500.61).category(Category.SUBSCRIPTIONS).build();
                Transaction t11 = new Transaction.Builder().createdAt(LocalDateTime.now().minusDays(10)).updatedAt(LocalDateTime.now()).description("diesel").user(mockUser).amount(1000.33).category(Category.TRAVEL).build();

                transactionRepo.saveAll(List.of(t1, t2, t3, t4, t5 ,t6 ,t7 ,t8, t9, t10,t11));

                System.out.println("Mock data seeded.");
            } else {
                System.out.println("Skipping mock data — users already exist.");
            }
        };
    }
}

