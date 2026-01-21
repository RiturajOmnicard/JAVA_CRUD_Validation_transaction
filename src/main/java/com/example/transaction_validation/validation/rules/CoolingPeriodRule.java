package com.example.transaction_validation.validation.rules;

import com.example.transaction_validation.constants.Channel;
import com.example.transaction_validation.constants.RuleStatus;
import com.example.transaction_validation.constants.RuleType;
import com.example.transaction_validation.dto.request.TransactionLogRequestDTO;
import com.example.transaction_validation.entity.TransactionLog;
import com.example.transaction_validation.entity.ValidationRule;
import com.example.transaction_validation.repository.TransactionLogRepository;
import com.example.transaction_validation.repository.ValidationRuleRepository;
import com.example.transaction_validation.utils.ChannelUtils;
import com.example.transaction_validation.utils.RuleUtils;
import com.example.transaction_validation.validation.RuleValidator;
import com.example.transaction_validation.validation.ValidationResult;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class CoolingPeriodRule implements RuleValidator {

    private final ValidationRuleRepository ruleRepository;
    private final TransactionLogRepository logRepository;

    public CoolingPeriodRule(ValidationRuleRepository ruleRepository, TransactionLogRepository logRepository) {
        this.ruleRepository = ruleRepository;
        this.logRepository = logRepository;
    }

    @Override
    public ValidationResult validate(TransactionLogRequestDTO request) {

        List<ValidationRule> rules = ruleRepository.findByStatusAndChannel(RuleStatus.ACTIVE, ChannelUtils.parseChannel(request.getChannel()));

        BigDecimal seconds = RuleUtils.getRuleValue(rules, RuleType.COOLING_PERIOD);
        if (seconds == null) return ValidationResult.allowed();

        TransactionLog last = logRepository.findTopByUserIdOrderByTransactionTimeDesc(request.getUserId());
        if (last == null || last.getTransactionTime() == null) return ValidationResult.allowed();

        LocalDateTime lastTime = last.getTransactionTime();
        LocalDateTime now = LocalDateTime.now();

        long diff = Duration.between(lastTime, now).getSeconds();

        if (diff < seconds.longValue()) {
            return ValidationResult.blocked(
                    "COOLING_PERIOD_VIOLATION",
                    "Transaction blocked due to cooling period rule."
            );
        }

        return ValidationResult.allowed();
    }
}
