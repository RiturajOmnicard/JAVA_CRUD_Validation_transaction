package com.example.transaction_validation.service.impl;

import com.example.transaction_validation.constants.RuleStatus;
import com.example.transaction_validation.entity.ValidationRule;
import com.example.transaction_validation.repository.ValidationRuleRepository;
import com.example.transaction_validation.service.ValidationRuleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ValidationRuleServiceImpl implements ValidationRuleService {

    private final ValidationRuleRepository ruleRepository;

    public ValidationRuleServiceImpl(ValidationRuleRepository ruleRepository) {
        this.ruleRepository = ruleRepository;
    }

    @Override
    public ValidationRule saveRule(ValidationRule rule) {
        List<ValidationRule> existingRules =
                ruleRepository.findByRuleTypeAndChannelAndStatus(
                        rule.getRuleType(),
                        rule.getChannel(),
                        RuleStatus.ACTIVE
                );
        if (!existingRules.isEmpty()) {
            ValidationRule existing = existingRules.get(0);
            existing.setRuleValue(rule.getRuleValue());
            existing.setStatus(rule.getStatus());
            return ruleRepository.save(existing);
        }
        return ruleRepository.save(rule);
    }
    @Override
    public List<ValidationRule> getActiveRules() {
        return ruleRepository.findByStatus(RuleStatus.ACTIVE);
    }
}
