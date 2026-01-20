package com.example.transaction_validation.service;

import com.example.transaction_validation.entity.ValidationRule;

import java.util.List;

public interface ValidationRuleService {

    ValidationRule saveRule(ValidationRule rule);

    List<ValidationRule> getActiveRules();
}
