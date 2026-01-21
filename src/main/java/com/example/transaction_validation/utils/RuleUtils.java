package com.example.transaction_validation.utils;

import com.example.transaction_validation.constants.RuleType;
import com.example.transaction_validation.entity.ValidationRule;

import java.math.BigDecimal;
import java.util.List;

public class RuleUtils {

    private RuleUtils() {
    }

    public static BigDecimal getRuleValue(List<ValidationRule> rules, RuleType ruleType) {

        if (rules == null || rules.isEmpty()) {
            return null;
        }

        for (ValidationRule rule : rules) {
            if (rule.getRuleType() != null && rule.getRuleType() == ruleType) {
                return rule.getRuleValue();
            }
        }

        return null;
    }
}
