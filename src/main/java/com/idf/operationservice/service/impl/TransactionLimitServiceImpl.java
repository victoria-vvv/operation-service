package com.idf.operationservice.service.impl;

import com.idf.operationservice.domain.dto.request.TransactionLimitDto;
import com.idf.operationservice.domain.entity.TransactionLimit;
import com.idf.operationservice.mapper.TransactionLimitMapper;
import com.idf.operationservice.repository.TransactionLimitRepository;
import com.idf.operationservice.service.TransactionLimitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Сервис, реализующий методы для работы с лимитами расходных операций
 */
@Service
@RequiredArgsConstructor
public class TransactionLimitServiceImpl implements TransactionLimitService {

    private final TransactionLimitRepository transactionLimitRepository;
    private final TransactionLimitMapper transactionLimitMapper;
    private final CacheServiceImpl cacheService;

    /**
     * Метод добавляет новый лимит по заданной категории
     *
     * @param transactionLimitDto Dto, включающее категорию расходов и устанавливаемую сумму лимита
     * @return сохраненный в БД новый объект лимита транзакции TransactionLimit
     */
    @Transactional
    public TransactionLimit addNewLimit(TransactionLimitDto transactionLimitDto) {

        TransactionLimit newLimit = transactionLimitMapper.mapToTransactionLimit(transactionLimitDto);

        TransactionLimit savedTransactionLimit = transactionLimitRepository.save(newLimit);

        cacheService.cacheNewLimit(transactionLimitDto);

        return savedTransactionLimit;
    }
}