package com.example.transaction_validation.validation.rules;

import com.example.transaction_validation.constants.Channel;
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
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class DailyTransactionCountRule implements RuleValidator {

    private final ValidationRuleRepository ruleRepository;
    private final TransactionLogRepository logRepository;

    public DailyTransactionCountRule(ValidationRuleRepository ruleRepository, TransactionLogRepository logRepository) {
        this.ruleRepository = ruleRepository;
        this.logRepository = logRepository;
    }

    @Override
    public ValidationResult validate(TransactionLogRequestDTO request) {

        List<ValidationRule> rules = ruleRepository.findByStatusAndChannel(RuleStatus.ACTIVE, ChannelUtils.parseChannel(request.getChannel()));

        BigDecimal limit = RuleUtils.getRuleValue(rules, RuleType.DAILY_TXN_COUNT);
        if (limit == null) return ValidationResult.allowed();

        LocalDate today = LocalDate.now();
        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end = today.plusDays(1).atStartOfDay().minusSeconds(1);

        long count = logRepository.countByUserIdAndTransactionTimeBetween(request.getUserId(), start, end);

        if (count >= limit.longValue()) {
            return ValidationResult.blocked(
                    "DAILY_TRANSACTION_COUNT_EXCEEDED",
                    "Daily transaction count limit exceeded."
            );
        }

        return ValidationResult.allowed();
    }
}
