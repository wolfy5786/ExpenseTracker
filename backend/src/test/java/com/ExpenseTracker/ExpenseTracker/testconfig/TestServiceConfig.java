package com.ExpenseTracker.ExpenseTracker.testconfig;

import com.ExpenseTracker.ExpenseTracker.service.TransactionService;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestServiceConfig {

    @Bean
    public TransactionService transactionService() {
        return Mockito.mock(TransactionService.class);
    }
}
