package com.example.transaction_validation.utils;

import com.example.transaction_validation.constants.Channel;
import com.example.transaction_validation.constants.RuleStatus;
import com.example.transaction_validation.constants.RuleType;
import com.example.transaction_validation.entity.ValidationRule;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class RuleUtilsTest {

    @Test
    void getRuleValue_shouldReturnNull_whenRulesListIsNull() {
        assertNull(RuleUtils.getRuleValue(null, RuleType.MAX_AMOUNT));
    }

    @Test
    void getRuleValue_shouldReturnNull_whenRulesListIsEmpty() {
        assertNull(RuleUtils.getRuleValue(Collections.emptyList(), RuleType.MAX_AMOUNT));
    }

    @Test
    void getRuleValue_shouldReturnCorrectValue_whenRuleExists() {
        ValidationRule r1 = new ValidationRule();
        r1.setRuleType(RuleType.MAX_AMOUNT);
        r1.setRuleValue(new BigDecimal("10000"));
        r1.setChannel(Channel.UPI);
        r1.setStatus(RuleStatus.ACTIVE);

        ValidationRule r2 = new ValidationRule();
        r2.setRuleType(RuleType.MIN_AMOUNT);
        r2.setRuleValue(new BigDecimal("10"));
        r2.setChannel(Channel.UPI);
        r2.setStatus(RuleStatus.ACTIVE);

        BigDecimal value = RuleUtils.getRuleValue(Arrays.asList(r1, r2), RuleType.MAX_AMOUNT);

        assertEquals(new BigDecimal("10000"), value);
    }

    @Test
    void getRuleValue_shouldReturnNull_whenRuleDoesNotExist() {
        ValidationRule r1 = new ValidationRule();
        r1.setRuleType(RuleType.MIN_AMOUNT);
        r1.setRuleValue(new BigDecimal("10"));

        BigDecimal value = RuleUtils.getRuleValue(Collections.singletonList(r1), RuleType.MAX_AMOUNT);

        assertNull(value);
    }
}
