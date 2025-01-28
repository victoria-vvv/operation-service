package com.idf.operationservice.controller;

import com.idf.operationservice.domain.dto.request.ExpenseTransactionDto;
import com.idf.operationservice.exception.handler.ExceptionResponseDto;
import com.idf.operationservice.service.impl.ExpenseTransactionServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер предоставляет API для интеграции с банковскими сервисами
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/transactions")
public class ExpenseTransactionController {

    private final ExpenseTransactionServiceImpl expenseTransactionService;

    /**
     * Эндпоинт для передачи данных о транзакции
     */
    @Operation(summary = "Передача данных расходной транзакции")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Транзакция передана успешно"),
        @ApiResponse(responseCode = "400", description = "Некорректный запрос",
            content = {@Content(schema = @Schema(implementation = ExceptionResponseDto.class))}),
        @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера",
            content = {@Content(schema = @Schema(implementation = ExceptionResponseDto.class))})
    })
    @PostMapping("/expense-transaction")
    @ResponseStatus(HttpStatus.OK)
    public void createTransaction(@RequestBody ExpenseTransactionDto transactionDTO) {
        expenseTransactionService.processTransaction(transactionDTO);
    }
}
