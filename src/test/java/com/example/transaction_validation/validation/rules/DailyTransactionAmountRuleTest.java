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

class DailyTransactionAmountRuleTest {

    @Test
    void validate_shouldAllow_whenTotalNotExceedsLimit() {
        ValidationRuleRepository ruleRepo = Mockito.mock(ValidationRuleRepository.class);
        TransactionLogRepository logRepo = Mockito.mock(TransactionLogRepository.class);

        ValidationRule amtRule = new ValidationRule();
        amtRule.setRuleType(RuleType.DAILY_TXN_AMOUNT);
        amtRule.setRuleValue(new BigDecimal("1000"));

        Mockito.when(ruleRepo.findByStatusAndChannel(RuleStatus.ACTIVE, Channel.UPI))
                .thenReturn(List.of(amtRule));

        Mockito.when(logRepo.sumAmountByUserIdAndTimeRange(eq("USR1"), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(new BigDecimal("600"));

        DailyTransactionAmountRule rule = new DailyTransactionAmountRule(ruleRepo, logRepo);

        TransactionLogRequestDTO req = new TransactionLogRequestDTO();
        req.setUserId("USR1");
        req.setAmount(new BigDecimal("200"));
        req.setChannel("UPI");

        ValidationResult result = rule.validate(req);

        assertTrue(result.isAllowed());
    }

    @Test
    void validate_shouldBlock_whenTotalExceedsLimit() {
        ValidationRuleRepository ruleRepo = Mockito.mock(ValidationRuleRepository.class);
        TransactionLogRepository logRepo = Mockito.mock(TransactionLogRepository.class);

        ValidationRule amtRule = new ValidationRule();
        amtRule.setRuleType(RuleType.DAILY_TXN_AMOUNT);
        amtRule.setRuleValue(new BigDecimal("1000"));

        Mockito.when(ruleRepo.findByStatusAndChannel(RuleStatus.ACTIVE, Channel.UPI))
                .thenReturn(List.of(amtRule));

        Mockito.when(logRepo.sumAmountByUserIdAndTimeRange(eq("USR1"), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(new BigDecimal("900"));

        DailyTransactionAmountRule rule = new DailyTransactionAmountRule(ruleRepo, logRepo);

        TransactionLogRequestDTO req = new TransactionLogRequestDTO();
        req.setUserId("USR1");
        req.setAmount(new BigDecimal("200"));
        req.setChannel("UPI");

        ValidationResult result = rule.validate(req);

        assertFalse(result.isAllowed());
        assertEquals("DAILY_TRANSACTION_AMOUNT_EXCEEDED", result.getErrorCode());
    }
}
