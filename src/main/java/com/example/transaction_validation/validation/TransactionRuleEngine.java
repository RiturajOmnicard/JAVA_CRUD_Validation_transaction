package com.example.transaction_validation.validation;

import com.example.transaction_validation.dto.request.TransactionLogRequestDTO;

import java.util.List;

public class TransactionRuleEngine {

    private final List<RuleValidator> rules;

    public TransactionRuleEngine(List<RuleValidator> rules) {
        this.rules = rules;
    }

    public ValidationResult validate(TransactionLogRequestDTO request) {
        for (RuleValidator rule : rules) {
            ValidationResult result = rule.validate(request);
            if (!result.isAllowed()) {
                return result;
            }
        }
        return ValidationResult.allowed();
    }
}
