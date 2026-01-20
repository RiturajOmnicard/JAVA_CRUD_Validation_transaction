package com.example.transaction_validation.service.impl;

import com.example.transaction_validation.constants.TransactionStatus;
import com.example.transaction_validation.dto.request.TransactionLogRequestDTO;
import com.example.transaction_validation.dto.response.ErrorDTO;
import com.example.transaction_validation.dto.response.TransactionLogResponseDTO;
import com.example.transaction_validation.entity.TransactionLog;
import com.example.transaction_validation.repository.TransactionLogRepository;
import com.example.transaction_validation.service.TransactionValidationService;
import com.example.transaction_validation.validation.RuleValidator;
import com.example.transaction_validation.validation.ValidationResult;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionValidationServiceImpl implements TransactionValidationService {

    private final TransactionLogRepository transactionLogRepository;
    private final List<RuleValidator> validators;

    public TransactionValidationServiceImpl(TransactionLogRepository transactionLogRepository,
                                            List<RuleValidator> validators) {
        this.transactionLogRepository = transactionLogRepository;
        this.validators = validators;
    }

    @Override
    public TransactionLogResponseDTO validateTransaction(TransactionLogRequestDTO request) {

        // Run all validations (stop at first failure)
        ValidationResult finalResult = ValidationResult.allowed();

        for (RuleValidator validator : validators) {
            ValidationResult result = validator.validate(request);

            if (!result.isAllowed()) {
                finalResult = result;
                break;
            }
        }

        TransactionStatus status = finalResult.isAllowed()
                ? TransactionStatus.ALLOWED
                : TransactionStatus.BLOCKED;

        // Save log to DB
        TransactionLog log = new TransactionLog();
        log.setUserId(request.getUserId());
        log.setAmount(request.getAmount());
        log.setStatus(status);
        log.setReasons(finalResult.isAllowed() ? null : finalResult.getErrorCode());
        log.setTransactionTime(LocalDateTime.now());

        transactionLogRepository.save(log);

        // Create Response DTO
        TransactionLogResponseDTO response = new TransactionLogResponseDTO();
        response.setStatus(status);

        if (finalResult.isAllowed()) {
            response.setError(null);
        } else {
            ErrorDTO error = new ErrorDTO();
            error.setCode(finalResult.getErrorCode());
            error.setMessage(finalResult.getErrorMessage());
            response.setError(error);
        }

        return response;
    }

    @Override
    public List<TransactionLog> getUserTransactions(String userId) {
        return transactionLogRepository.findByUserId(userId);
    }
}
