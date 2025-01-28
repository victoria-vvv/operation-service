package com.idf.operationservice.controller;

import com.idf.operationservice.domain.dto.request.TransactionLimitDto;
import com.idf.operationservice.domain.projection.ExceedingLimitResponse;
import com.idf.operationservice.exception.handler.ExceptionResponseDto;
import com.idf.operationservice.service.ExpenseTransactionService;
import com.idf.operationservice.service.impl.TransactionLimitServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Контроллер предоставляет API для внешних запросов клиента
 */
@RestController
@RequestMapping("/transaction-limit")
@RequiredArgsConstructor
public class ClientInteractionController {

    private final TransactionLimitServiceImpl transactionLimitService;
    private final ExpenseTransactionService expenseTransactionService;

    /**
     * Эндпоинт для добавления нового лимита для расходных транзакция
     *
     * @param transactionLimitDto включает категорию, по которым добавляется лимит и сумма лимита
     */
    @Operation(summary = "Добавление нового лимита по транзакциям")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Лимит добавлен упещно"),
        @ApiResponse(responseCode = "400", description = "Некорректный запрос",
            content = {@Content(schema = @Schema(implementation = ExceptionResponseDto.class))}),
        @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера",
            content = {@Content(schema = @Schema(implementation = ExceptionResponseDto.class))})
    })
    @PostMapping("/add-limit")
    @ResponseStatus(HttpStatus.OK)
    public void addLimit(@RequestBody TransactionLimitDto transactionLimitDto) {
        transactionLimitService.addNewLimit(transactionLimitDto);
    }

    /**
     * Эндпоинт для получения списка транзакций, по которым превышен лимит, актуальный на дату
     * осуществления транзакции
     */
    @Operation(summary = "Получения списка транзакций, по которым превышен лимит")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Список транзакций получен"),
        @ApiResponse(responseCode = "400", description = "Некорректный запрос",
            content = {@Content(schema = @Schema(implementation = ExceptionResponseDto.class))}),
        @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера",
            content = {@Content(schema = @Schema(implementation = ExceptionResponseDto.class))})
    })
    @GetMapping("/exceeding-limit")
    @ResponseStatus(HttpStatus.OK)
    public List<ExceedingLimitResponse> findExceedingLimitTransactions() {
        return expenseTransactionService.findExceedingLimitTransactions();
    }
}
