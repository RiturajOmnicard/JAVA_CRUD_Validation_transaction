package com.example.transaction_validation.service;

import com.example.transaction_validation.constants.TransactionStatus;
import com.example.transaction_validation.dto.request.TransactionLogRequestDTO;
import com.example.transaction_validation.dto.response.TransactionLogResponseDTO;
import com.example.transaction_validation.entity.TransactionLog;
import com.example.transaction_validation.repository.TransactionLogRepository;
import com.example.transaction_validation.repository.UserRepository;
import com.example.transaction_validation.service.impl.TransactionValidationServiceImpl;
import com.example.transaction_validation.service.impl.UserServiceImpl;
import com.example.transaction_validation.validation.RuleValidator;
import com.example.transaction_validation.validation.ValidationResult;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionValidationServiceImplTest {

    @Test
    void validateTransaction_shouldReturnAllowed_whenAllRulesPass() {
        TransactionLogRepository logRepo = Mockito.mock(TransactionLogRepository.class);
        UserRepository userRepo = Mockito.mock(UserRepository.class);

        RuleValidator rule1 = new RuleValidator() {
            @Override
            public ValidationResult validate(TransactionLogRequestDTO req) {
                return ValidationResult.allowed();
            }
        };

        RuleValidator rule2 = new RuleValidator() {
            @Override
            public ValidationResult validate(TransactionLogRequestDTO req) {
                return ValidationResult.allowed();
            }
        };

        UserService userService = new UserServiceImpl(userRepo);

        TransactionValidationService service =
                new TransactionValidationServiceImpl(logRepo, Arrays.asList(rule1, rule2), userService);

        TransactionLogRequestDTO request = new TransactionLogRequestDTO();
        request.setUserId("USR1");
        request.setAmount(new BigDecimal("100"));
        request.setChannel("UPI");

        TransactionLogResponseDTO response = service.validateTransaction(request);

        assertEquals(TransactionStatus.ALLOWED, response.getStatus());
        assertNull(response.getError());

        verify(logRepo, times(1)).save(any(TransactionLog.class));
    }

    @Test
    void validateTransaction_shouldReturnBlocked_whenAnyRuleFails() {
        TransactionLogRepository logRepo = Mockito.mock(TransactionLogRepository.class);
        UserRepository userRepo = Mockito.mock(UserRepository.class);

        RuleValidator failRule = new RuleValidator() {
            @Override
            public ValidationResult validate(TransactionLogRequestDTO req) {
                return ValidationResult.blocked("TEST_FAIL", "Test rule failed");
            }
        };

        UserService userService = new UserServiceImpl(userRepo);

        TransactionValidationService service =
                new TransactionValidationServiceImpl(logRepo, Collections.singletonList(failRule), userService);

        TransactionLogRequestDTO request = new TransactionLogRequestDTO();
        request.setUserId("USR1");
        request.setAmount(new BigDecimal("100"));
        request.setChannel("UPI");

        TransactionLogResponseDTO response = service.validateTransaction(request);

        assertEquals(TransactionStatus.BLOCKED, response.getStatus());
        assertNotNull(response.getError());
        assertEquals("TEST_FAIL", response.getError().getCode());

        verify(logRepo, times(1)).save(any(TransactionLog.class));
    }
}
