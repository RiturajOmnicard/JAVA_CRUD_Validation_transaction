package com.example.transaction_validation.validation.rules;

import com.example.transaction_validation.constants.Channel;
import com.example.transaction_validation.constants.RuleStatus;
import com.example.transaction_validation.constants.RuleType;
import com.example.transaction_validation.dto.request.TransactionLogRequestDTO;
import com.example.transaction_validation.entity.ValidationRule;
import com.example.transaction_validation.repository.TransactionLogRepository;
import com.example.transaction_validation.repository.ValidationRuleRepository;
import com.example.transaction_validation.validation.ValidationResult;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

class DailyTransactionCountRuleTest {

    @Test
    void validate_shouldAllow_whenCountReachedNotLimit() {
        ValidationRuleRepository ruleRepo = Mockito.mock(ValidationRuleRepository.class);
        TransactionLogRepository logRepo = Mockito.mock(TransactionLogRepository.class);

        ValidationRule countRule = new ValidationRule();
        countRule.setRuleType(RuleType.DAILY_TXN_COUNT);
        countRule.setRuleValue(new BigDecimal("5"));

        Mockito.when(ruleRepo.findByStatusAndChannel(RuleStatus.ACTIVE, Channel.UPI))
                .thenReturn(List.of(countRule));

        Mockito.when(logRepo.countByUserIdAndTransactionTimeBetween(eq("USR1"), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(3L);

        DailyTransactionCountRule rule = new DailyTransactionCountRule(ruleRepo, logRepo);

        TransactionLogRequestDTO req = new TransactionLogRequestDTO();
        req.setUserId("USR1");
        req.setAmount(new BigDecimal("100"));
        req.setChannel("UPI");

        ValidationResult result = rule.validate(req);

        assertTrue(result.isAllowed());
    }

    @Test
    void validate_shouldBlock_whenCountReachedLimit() {
        ValidationRuleRepository ruleRepo = Mockito.mock(ValidationRuleRepository.class);
        TransactionLogRepository logRepo = Mockito.mock(TransactionLogRepository.class);

        ValidationRule countRule = new ValidationRule();
        countRule.setRuleType(RuleType.DAILY_TXN_COUNT);
        countRule.setRuleValue(new BigDecimal("5"));

        Mockito.when(ruleRepo.findByStatusAndChannel(RuleStatus.ACTIVE, Channel.UPI))
                .thenReturn(List.of(countRule));

        Mockito.when(logRepo.countByUserIdAndTransactionTimeBetween(eq("USR1"), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(5L);

        DailyTransactionCountRule rule = new DailyTransactionCountRule(ruleRepo, logRepo);

        TransactionLogRequestDTO req = new TransactionLogRequestDTO();
        req.setUserId("USR1");
        req.setAmount(new BigDecimal("100"));
        req.setChannel("UPI");

        ValidationResult result = rule.validate(req);

        assertFalse(result.isAllowed());
        assertEquals("DAILY_TRANSACTION_COUNT_EXCEEDED", result.getErrorCode());
    }
}
