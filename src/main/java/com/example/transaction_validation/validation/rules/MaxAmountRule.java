package com.example.transaction_validation.validation.rules;

import com.example.transaction_validation.constants.Channel;
import com.example.transaction_validation.constants.RuleStatus;
import com.example.transaction_validation.constants.RuleType;
import com.example.transaction_validation.dto.request.TransactionLogRequestDTO;
import com.example.transaction_validation.entity.ValidationRule;
import com.example.transaction_validation.repository.ValidationRuleRepository;
import com.example.transaction_validation.utils.ChannelUtils;
import com.example.transaction_validation.utils.RuleUtils;
import com.example.transaction_validation.validation.RuleValidator;
import com.example.transaction_validation.validation.ValidationResult;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class MaxAmountRule implements RuleValidator {

    private final ValidationRuleRepository ruleRepository;

    public MaxAmountRule(ValidationRuleRepository ruleRepository) {
        this.ruleRepository = ruleRepository;
    }

    @Override
    public ValidationResult validate(TransactionLogRequestDTO request) {

        List<ValidationRule> rules = ruleRepository.findByStatusAndChannel(RuleStatus.ACTIVE, ChannelUtils.parseChannel(request.getChannel()));

        BigDecimal max = RuleUtils.getRuleValue(rules, RuleType.MAX_AMOUNT);
        if (max == null) return ValidationResult.allowed();

        if (request.getAmount().compareTo(max) > 0) {
            return ValidationResult.blocked(
                    "MAX_TRANSACTION_AMOUNT_EXCEEDED",
                    "Transaction amount exceeds the maximum allowed limit."
            );
        }

        return ValidationResult.allowed();
    }
}
