package com.idf.operationservice.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.idf.operationservice.domain.dto.request.TransactionLimitDto;
import com.idf.operationservice.domain.projection.ExceedingLimitResponse;
import com.idf.operationservice.service.ExpenseTransactionService;
import com.idf.operationservice.service.impl.TransactionLimitServiceImpl;
import com.idf.operationservice.util.ConstantUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

@WebMvcTest(ClientInteractionController.class)
class ClientInteractionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionLimitServiceImpl transactionLimitService;

    @MockBean
    private ExpenseTransactionService expenseTransactionService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("New limit added successfully")
    void addLimitSuccessfully() throws Exception {
        TransactionLimitDto transactionLimitDto = TransactionLimitDto.builder().category(ConstantUtil.SERVICE_CATEGORY)
            .limitValue(ConstantUtil.SERVICE_LIMIT_2).build();

        mockMvc.perform(post(ConstantUtil.ADD_LIMIT_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transactionLimitDto)))
            .andExpect(status().isOk());

        verify(transactionLimitService).addNewLimit(any(TransactionLimitDto.class));
    }

    @Test
    @DisplayName("Found transactions with exceeded limit successfully")
    void findExceedingLimitTransactionsSuccessfully() throws Exception {
        ExceedingLimitResponse response = ExceedingLimitResponse.builder()
            .accountFrom(ConstantUtil.ACCOUNT_FROM).accountTo(ConstantUtil.ACCOUNT_TO)
            .category(ConstantUtil.PRODUCT_CATEGORY).currency(ConstantUtil.USD)
            .transactionSum(ConstantUtil.TRANSACTION_SUM_1)
            .transactionTime(ConstantUtil.TRANSACTION_TIME_1).restLimitSum(ConstantUtil.REST_LIMIT_2)
            .limitValue(ConstantUtil.PRODUCT_LIMIT_1).limitCurrency(ConstantUtil.USD)
            .limitSettingTime(ConstantUtil.LIMIT_SET_TIME_1).build();

        when(expenseTransactionService.findExceedingLimitTransactions()).thenReturn(Collections.singletonList(response));

        mockMvc.perform(get(ConstantUtil.FIND_LIMITS_URL))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].accountFrom").value(ConstantUtil.ACCOUNT_FROM))
            .andExpect(jsonPath("$[0].accountTo").value(ConstantUtil.ACCOUNT_TO))
            .andExpect(jsonPath("$[0].category").value(ConstantUtil.PRODUCT_CATEGORY))
            .andExpect(jsonPath("$[0].currency").value(ConstantUtil.USD))
            .andExpect(jsonPath("$[0].transactionSum").value(ConstantUtil.TRANSACTION_SUM_1))
            .andExpect(jsonPath("$[0].restLimitSum").value(ConstantUtil.REST_LIMIT_2))
            .andExpect(jsonPath("$[0].limitCurrency").value(ConstantUtil.USD));

        verify(expenseTransactionService).findExceedingLimitTransactions();
    }

    @Test
    @DisplayName("Transactions with exceeded limit absent")
    void transactionsWithExceededLimitAbsent() throws Exception {
        when(expenseTransactionService.findExceedingLimitTransactions()).thenReturn(Collections.emptyList());

        mockMvc.perform(get(ConstantUtil.FIND_LIMITS_URL))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isEmpty());

        verify(expenseTransactionService).findExceedingLimitTransactions();
    }
}
