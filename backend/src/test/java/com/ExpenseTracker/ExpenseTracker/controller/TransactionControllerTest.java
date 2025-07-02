package com.ExpenseTracker.ExpenseTracker.controller;

import com.ExpenseTracker.ExpenseTracker.dto.TransactionDTO;
import com.ExpenseTracker.ExpenseTracker.model.Category;
import com.ExpenseTracker.ExpenseTracker.service.TransactionService;
import com.ExpenseTracker.ExpenseTracker.testconfig.TestSecurityConfig;
import com.ExpenseTracker.ExpenseTracker.testconfig.TestServiceConfig;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Import({TestServiceConfig.class, TestSecurityConfig.class})
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "testuser")
    void testCreateTransaction() throws Exception {

        TransactionDTO request = new TransactionDTO(50.0, Category.GROCERIES,"Test transaction");

        TransactionDTO response = new TransactionDTO(1L,LocalDateTime.now(),LocalDateTime.now(),request.getAmount(),request.getCategory(),request.getDescription());

        when(transactionService.createTransaction(eq("testuser"), any(TransactionDTO.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk()).andDo(print())
                .andExpect(jsonPath("$.transactionId").value(1L))
                .andExpect(jsonPath("$.amount").value(50.0))
                .andExpect(jsonPath("$.category").value("GROCERIES"));
    }
    @Test
    @WithMockUser(username = "testuser")
    void testGetAllTransactions() throws Exception {
        TransactionDTO transaction = new TransactionDTO(1L, LocalDateTime.now(), LocalDateTime.now(), 50.0, Category.GROCERIES, "Test transaction");
        when(transactionService.getAllTransactions("testuser")).thenReturn(List.of(transaction));

        mockMvc.perform(get("/api/transactions"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].amount").value(50.0))
                .andExpect(jsonPath("$[0].category").value("GROCERIES"));
    }

    @Test
    @WithMockUser(username = "testuser")
    void testGetTransactionsByCategory() throws Exception {
        TransactionDTO transaction = new TransactionDTO(1L, LocalDateTime.now(), LocalDateTime.now(), 30.0, Category.GROCERIES, "Groceries again");
        when(transactionService.getTransactionsByCategory(eq("testuser"), eq(Category.GROCERIES)))
                .thenReturn(List.of(transaction));

        mockMvc.perform(get("/api/transactions/category")
                        .param("category", "GROCERIES"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].category").value("GROCERIES"));
    }

    @Test
    @WithMockUser(username = "testuser")
    void testGetTransactionsBetweenDates() throws Exception {
        LocalDateTime from = LocalDateTime.of(2024, 1, 1, 0, 0);
        LocalDateTime to = LocalDateTime.of(2024, 1, 5, 0, 0);

        TransactionDTO transaction = new TransactionDTO(1L, from, to, 100.0, Category.GROCERIES, "Groceries");

        when(transactionService.getTransactionsBetweenDates(eq("testuser"), any(), any()))
                .thenReturn(List.of(transaction));

        mockMvc.perform(get("/api/transactions/range")
                        .param("from", from.toString())
                        .param("to", to.toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].description").value("Groceries"))
                .andExpect(jsonPath("$[0].amount").value(100.0));
    }


    @Test
    @WithMockUser(username = "testuser")
    void testGetTransactionByCategoryBetweenDates() throws Exception {
        // Define consistent test dates
        LocalDateTime from = LocalDateTime.of(2024, 1, 1, 0, 0);
        LocalDateTime to = LocalDateTime.of(2025, 10, 5, 0, 0);

        TransactionDTO transaction = new TransactionDTO(
                1L, from, to, 200.0, Category.MISCELLANEOUS, "Movies");

        // Mock the service
        when(transactionService.getTransactionByCategoryBetweenDates(
                eq("testuser"), eq(Category.MISCELLANEOUS), any(), any()))
                .thenReturn(List.of(transaction));

        mockMvc.perform(get("/api/transactions/category-range")
                        .param("category", "MISCELLANEOUS")
                        .param("from", from.toString()) // Use ISO format
                        .param("to", to.toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].description").value("Movies"))
                .andExpect(jsonPath("$[0].amount").value(200.0))
                .andExpect(jsonPath("$[0].category").value("MISCELLANEOUS"));
    }



    @Test
    @WithMockUser(username = "testuser")
    void testGetTopNTransactions() throws Exception {
        TransactionDTO transaction = new TransactionDTO(1L, LocalDateTime.now(), LocalDateTime.now(), 150.0, Category.RENT, "Monthly rent");

        when(transactionService.getTopNTransactions("testuser", 1, true)).thenReturn(List.of(transaction));

        mockMvc.perform(get("/api/transactions/top")
                        .param("n", "1")
                        .param("ascending", "true"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].amount").value(150.0));
    }

    @Test
    @WithMockUser(username = "testuser")
    void testUpdateTransaction() throws Exception {
        TransactionDTO request = new TransactionDTO(300.0, Category.RENT, "Updated rent");
        TransactionDTO response = new TransactionDTO(1L, LocalDateTime.now(), LocalDateTime.now(), 300.0, Category.RENT, "Updated rent");

        when(transactionService.updateTransaction(eq("testuser"), eq(1L), any(TransactionDTO.class)))
                .thenReturn(response);

        mockMvc.perform(put("/api/transactions/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Updated rent"))
                .andExpect(jsonPath("$.amount").value(300.0));
    }

    @Test
    @WithMockUser(username = "testuser")
    void testDeleteTransaction() throws Exception {
        mockMvc.perform(delete("/api/transactions/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

}
