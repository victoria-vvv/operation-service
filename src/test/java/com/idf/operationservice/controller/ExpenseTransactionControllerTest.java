package com.idf.operationservice.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.idf.operationservice.domain.dto.request.ExpenseTransactionDto;
import com.idf.operationservice.service.impl.ExpenseTransactionServiceImpl;
import com.idf.operationservice.util.ConstantUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ExpenseTransactionController.class)
class ExpenseTransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ExpenseTransactionServiceImpl expenseTransactionService;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("Transaction processed successfully")
    void processTransactionSuccessfully() throws Exception {
        ExpenseTransactionDto transactionDto = ExpenseTransactionDto.builder()
            .category(ConstantUtil.PRODUCT_CATEGORY).transactionSum(ConstantUtil.TRANSACTION_SUM_1)
            .currency(ConstantUtil.KZT).build();

        mockMvc.perform(post(ConstantUtil.TRANSACTION_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transactionDto)))
            .andExpect(status().isOk());

        verify(expenseTransactionService).processTransaction(any(ExpenseTransactionDto.class));
    }
}
