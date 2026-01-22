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

class MaxAmountRuleTest {

    @Test
    void validate_shouldAllow_whenMaxRuleNotConfigured() {
        ValidationRuleRepository repo = Mockito.mock(ValidationRuleRepository.class);
        MaxAmountRule rule = new MaxAmountRule(repo);

        TransactionLogRequestDTO req = new TransactionLogRequestDTO();
        req.setUserId("USR1");
        req.setAmount(new BigDecimal("5000"));
        req.setChannel("UPI");

        when(repo.findByStatusAndChannel(RuleStatus.ACTIVE, Channel.UPI))
                .thenReturn(Collections.emptyList());

        ValidationResult result = rule.validate(req);

        assertTrue(result.isAllowed());
    }

    @Test
    void validate_shouldBlock_whenAmountExceedsMax() {
        ValidationRuleRepository repo = Mockito.mock(ValidationRuleRepository.class);
        MaxAmountRule rule = new MaxAmountRule(repo);

        TransactionLogRequestDTO req = new TransactionLogRequestDTO();
        req.setUserId("USR1");
        req.setAmount(new BigDecimal("20000"));
        req.setChannel("UPI");

        ValidationRule maxRule = new ValidationRule();
        maxRule.setRuleType(RuleType.MAX_AMOUNT);
        maxRule.setRuleValue(new BigDecimal("10000"));
        maxRule.setChannel(Channel.UPI);
        maxRule.setStatus(RuleStatus.ACTIVE);

        when(repo.findByStatusAndChannel(RuleStatus.ACTIVE, Channel.UPI))
                .thenReturn(Collections.singletonList(maxRule));

        ValidationResult result = rule.validate(req);

        assertFalse(result.isAllowed());
        assertEquals("MAX_TRANSACTION_AMOUNT_EXCEEDED", result.getErrorCode());
    }
}
