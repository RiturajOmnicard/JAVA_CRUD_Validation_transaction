package com.example.transaction_validation.validation.rules;

import com.example.transaction_validation.constants.RuleStatus;
import com.example.transaction_validation.constants.RuleType;
import com.example.transaction_validation.dto.request.TransactionLogRequestDTO;
import com.example.transaction_validation.entity.ValidationRule;
import com.example.transaction_validation.repository.TransactionLogRepository;
import com.example.transaction_validation.repository.ValidationRuleRepository;
import com.example.transaction_validation.utils.ChannelUtils;
import com.example.transaction_validation.utils.RuleUtils;
import com.example.transaction_validation.validation.RuleValidator;
import com.example.transaction_validation.validation.ValidationResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class HighVelocityRule implements RuleValidator {

    private final ValidationRuleRepository ruleRepository;
    private final TransactionLogRepository logRepository;

    @Value("${highvelocity.window.minutes:1}")
    private long windowMinutes;

    public HighVelocityRule(ValidationRuleRepository ruleRepository, TransactionLogRepository logRepository) {
        this.ruleRepository = ruleRepository;
        this.logRepository = logRepository;
    }

    @Override
    public ValidationResult validate(TransactionLogRequestDTO request) {

        List<ValidationRule> rules = ruleRepository.findByStatusAndChannel(RuleStatus.ACTIVE, ChannelUtils.parseChannel(request.getChannel()));

        BigDecimal maxCount = RuleUtils.getRuleValue(rules, RuleType.HIGH_VELOCITY);
        if (maxCount == null) return ValidationResult.allowed();

        //i used a 1 minute window, we can use env to configure it.
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start = now.minusMinutes(windowMinutes);

        long count = logRepository.countByUserIdAndTransactionTimeBetween(request.getUserId(), start, now);

        if (count >= maxCount.longValue()) {
            return ValidationResult.blocked(
                    "HIGH_VELOCITY_DETECTED",
                    "Too many transactions in a short time window."
            );
        }

        return ValidationResult.allowed();
    }
}
