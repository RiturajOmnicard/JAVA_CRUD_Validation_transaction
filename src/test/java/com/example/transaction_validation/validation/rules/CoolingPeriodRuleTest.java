package com.example.transaction_validation.validation.rules;

import com.example.transaction_validation.constants.Channel;
import com.example.transaction_validation.constants.RuleStatus;
import com.example.transaction_validation.constants.RuleType;
import com.example.transaction_validation.dto.request.TransactionLogRequestDTO;
import com.example.transaction_validation.entity.TransactionLog;
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

class CoolingPeriodRuleTest {

    @Test
    void validate_shouldAllow_whenNotInCoolingPeriod() {
        ValidationRuleRepository ruleRepo = Mockito.mock(ValidationRuleRepository.class);
        TransactionLogRepository logRepo = Mockito.mock(TransactionLogRepository.class);

        ValidationRule coolingRule = new ValidationRule();
        coolingRule.setRuleType(RuleType.COOLING_PERIOD);
        coolingRule.setRuleValue(new BigDecimal("30"));

        Mockito.when(ruleRepo.findByStatusAndChannel(RuleStatus.ACTIVE, Channel.UPI))
                .thenReturn(List.of(coolingRule));

        TransactionLog lastLog = new TransactionLog();
        lastLog.setTransactionTime(LocalDateTime.now().minusSeconds(10));

        Mockito.when(logRepo.findTopByUserIdOrderByTransactionTimeDesc("USR1"))
                .thenReturn(lastLog);

        CoolingPeriodRule rule = new CoolingPeriodRule(ruleRepo, logRepo);

        TransactionLogRequestDTO req = new TransactionLogRequestDTO();
        req.setUserId("USR1");
        req.setAmount(new BigDecimal("100"));
        req.setChannel("UPI");

        ValidationResult result = rule.validate(req);

        assertFalse(result.isAllowed());
        assertEquals("COOLING_PERIOD_VIOLATION", result.getErrorCode());
    }

    @Test
    void validate_shouldBlock_whenWithinCoolingPeriod() {
        ValidationRuleRepository ruleRepo = Mockito.mock(ValidationRuleRepository.class);
        TransactionLogRepository logRepo = Mockito.mock(TransactionLogRepository.class);

        ValidationRule coolingRule = new ValidationRule();
        coolingRule.setRuleType(RuleType.COOLING_PERIOD);
        coolingRule.setRuleValue(new BigDecimal("30"));

        Mockito.when(ruleRepo.findByStatusAndChannel(RuleStatus.ACTIVE, Channel.UPI))
                .thenReturn(List.of(coolingRule));

        TransactionLog lastLog = new TransactionLog();
        lastLog.setTransactionTime(LocalDateTime.now().minusSeconds(10));

        Mockito.when(logRepo.findTopByUserIdOrderByTransactionTimeDesc("USR1"))
                .thenReturn(lastLog);

        CoolingPeriodRule rule = new CoolingPeriodRule(ruleRepo, logRepo);

        TransactionLogRequestDTO req = new TransactionLogRequestDTO();
        req.setUserId("USR1");
        req.setAmount(new BigDecimal("100"));
        req.setChannel("UPI");

        ValidationResult result = rule.validate(req);

        assertFalse(result.isAllowed());
        assertEquals("COOLING_PERIOD_VIOLATION", result.getErrorCode());
    }
}
