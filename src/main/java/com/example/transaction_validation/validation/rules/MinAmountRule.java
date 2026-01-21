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
public class MinAmountRule implements RuleValidator {

    private final ValidationRuleRepository ruleRepository;

    public MinAmountRule(ValidationRuleRepository ruleRepository) {
        this.ruleRepository = ruleRepository;
    }

    @Override
    public ValidationResult validate(TransactionLogRequestDTO request) {

        List<ValidationRule> rules = ruleRepository.findByStatusAndChannel(RuleStatus.ACTIVE, ChannelUtils.parseChannel(request.getChannel()));

        BigDecimal min = RuleUtils.getRuleValue(rules, RuleType.MIN_AMOUNT);
        if (min == null) return ValidationResult.allowed();

        if (request.getAmount().compareTo(min) < 0) {
            return ValidationResult.blocked(
                    "MIN_TRANSACTION_AMOUNT_VIOLATION",
                    "Transaction amount is below the minimum allowed limit."
            );
        }

        return ValidationResult.allowed();
    }
}
