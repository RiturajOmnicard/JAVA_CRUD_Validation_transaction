package com.example.transaction_validation.validation.rules;

import com.example.transaction_validation.constants.Channel;
import com.example.transaction_validation.constants.RuleStatus;
import com.example.transaction_validation.constants.RuleType;
import com.example.transaction_validation.dto.request.TransactionLogRequestDTO;
import com.example.transaction_validation.entity.ValidationRule;
import com.example.transaction_validation.repository.ValidationRuleRepository;
import com.example.transaction_validation.validation.RuleUtils;
import com.example.transaction_validation.validation.RuleValidator;
import com.example.transaction_validation.validation.ValidationResult;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class ChannelLimitRule implements RuleValidator {

    private final ValidationRuleRepository ruleRepository;

    public ChannelLimitRule(ValidationRuleRepository ruleRepository) {
        this.ruleRepository = ruleRepository;
    }

    @Override
    public ValidationResult validate(TransactionLogRequestDTO request) {

        List<ValidationRule> rules = ruleRepository.findByStatusAndChannel(RuleStatus.ACTIVE, Channel.valueOf(request.getChannel()));

        BigDecimal limit = RuleUtils.getRuleValue(rules, RuleType.CHANNEL_LIMIT);
        if (limit == null) return ValidationResult.allowed();

        if (request.getAmount().compareTo(limit) > 0) {
            return ValidationResult.blocked(
                    "CHANNEL_LIMIT_EXCEEDED",
                    "Transaction amount exceeds the allowed channel limit."
            );
        }

        return ValidationResult.allowed();
    }
}
