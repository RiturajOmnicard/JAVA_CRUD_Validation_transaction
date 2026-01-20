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
import java.time.LocalTime;
import java.util.List;

@Component
public class TimeWindowRule implements RuleValidator {

    private final ValidationRuleRepository ruleRepository;

    public TimeWindowRule(ValidationRuleRepository ruleRepository) {
        this.ruleRepository = ruleRepository;
    }

    @Override
    public ValidationResult validate(TransactionLogRequestDTO request) {

        List<ValidationRule> rules = ruleRepository.findByStatusAndChannel(
                RuleStatus.ACTIVE,
                Channel.valueOf(request.getChannel())
        );

        BigDecimal startHourVal = RuleUtils.getRuleValue(rules, RuleType.TIME_WINDOW_START_HOUR);
        BigDecimal endHourVal = RuleUtils.getRuleValue(rules, RuleType.TIME_WINDOW_END_HOUR);

        if (startHourVal == null || endHourVal == null) {
            return ValidationResult.allowed();
        }

        int startHour = startHourVal.intValue();
        int endHour = endHourVal.intValue();

        LocalTime now = LocalTime.now();
        LocalTime start = LocalTime.of(startHour, 0);
        LocalTime end = LocalTime.of(endHour, 0);

        boolean allowed;

        // Handles normal case: 9 -> 18
        if (start.isBefore(end) || start.equals(end)) {
            allowed = !now.isBefore(start) && !now.isAfter(end);
        } else {
            // Handles overnight window: 22 -> 6
            allowed = !now.isBefore(start) || !now.isAfter(end);
        }

        if (!allowed) {
            return ValidationResult.blocked(
                    "TIME_WINDOW_VIOLATION",
                    "Transaction is not allowed during this time window."
            );
        }

        return ValidationResult.allowed();
    }
}
