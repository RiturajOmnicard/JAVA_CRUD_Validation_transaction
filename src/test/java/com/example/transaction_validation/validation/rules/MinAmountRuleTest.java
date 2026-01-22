package com.example.transaction_validation.validation.rules;

import com.example.transaction_validation.constants.Channel;
import com.example.transaction_validation.constants.RuleStatus;
import com.example.transaction_validation.constants.RuleType;
import com.example.transaction_validation.dto.request.TransactionLogRequestDTO;
import com.example.transaction_validation.entity.ValidationRule;
import com.example.transaction_validation.repository.ValidationRuleRepository;
import com.example.transaction_validation.validation.ValidationResult;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class MinAmountRuleTest {

    @Test
    void validate_shouldBlock_whenAmountBelowMin() {
        ValidationRuleRepository repo = Mockito.mock(ValidationRuleRepository.class);
        MinAmountRule rule = new MinAmountRule(repo);

        TransactionLogRequestDTO req = new TransactionLogRequestDTO();
        req.setUserId("USR1");
        req.setAmount(new BigDecimal("5"));
        req.setChannel("UPI");

        ValidationRule minRule = new ValidationRule();
        minRule.setRuleType(RuleType.MIN_AMOUNT);
        minRule.setRuleValue(new BigDecimal("10"));
        minRule.setChannel(Channel.UPI);
        minRule.setStatus(RuleStatus.ACTIVE);

        when(repo.findByStatusAndChannel(RuleStatus.ACTIVE, Channel.UPI))
                .thenReturn(Collections.singletonList(minRule));

        ValidationResult result = rule.validate(req);

        assertFalse(result.isAllowed());
        assertEquals("MIN_TRANSACTION_AMOUNT_VIOLATION", result.getErrorCode());
    }

    @Test
    void validate_shouldAllow_whenAmountAboveMin() {
        ValidationRuleRepository repo = Mockito.mock(ValidationRuleRepository.class);
        MinAmountRule rule = new MinAmountRule(repo);

        TransactionLogRequestDTO req = new TransactionLogRequestDTO();
        req.setUserId("USR1");
        req.setAmount(new BigDecimal("50"));
        req.setChannel("UPI");

        ValidationRule minRule = new ValidationRule();
        minRule.setRuleType(RuleType.MIN_AMOUNT);
        minRule.setRuleValue(new BigDecimal("10"));
        minRule.setChannel(Channel.UPI);
        minRule.setStatus(RuleStatus.ACTIVE);

        when(repo.findByStatusAndChannel(RuleStatus.ACTIVE, Channel.UPI))
                .thenReturn(Collections.singletonList(minRule));

        ValidationResult result = rule.validate(req);

        assertTrue(result.isAllowed());
    }

    @Test
    void validate_shouldAllow_whenMaxRuleNotConfigured() {
        ValidationRuleRepository repo = Mockito.mock(ValidationRuleRepository.class);
        MinAmountRule rule = new MinAmountRule(repo);

        TransactionLogRequestDTO req = new TransactionLogRequestDTO();
        req.setUserId("USR1");
        req.setAmount(new BigDecimal("5000"));
        req.setChannel("UPI");

        when(repo.findByStatusAndChannel(RuleStatus.ACTIVE, Channel.UPI))
                .thenReturn(Collections.emptyList());

        ValidationResult result = rule.validate(req);

        assertTrue(result.isAllowed());
    }
}
