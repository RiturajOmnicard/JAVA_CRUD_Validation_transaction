package com.example.transaction_validation.validation.rules;

import com.example.transaction_validation.constants.RuleStatus;
import com.example.transaction_validation.constants.RuleType;
import com.example.transaction_validation.dto.request.TransactionLogRequestDTO;
import com.example.transaction_validation.entity.ValidationRule;
import com.example.transaction_validation.repository.ValidationRuleRepository;
import com.example.transaction_validation.utils.ChannelUtils;
import com.example.transaction_validation.utils.RuleUtils;
import com.example.transaction_validation.validation.RuleValidator;
import com.example.transaction_validation.validation.ValidationResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

@Component
public class TimeWindowRule implements RuleValidator {

    private final ValidationRuleRepository ruleRepository;

    @Value("${time.window.start.hour:9}")
    private int startHour;

    @Value("${time.window.end.hour:18}")
    private int endHour;

    public TimeWindowRule(ValidationRuleRepository ruleRepository) {
        this.ruleRepository = ruleRepository;
    }

    @Override
    public ValidationResult validate(TransactionLogRequestDTO request) {

        // Fetch active rules for channel
        List<ValidationRule> rules = ruleRepository.findByStatusAndChannel(
                RuleStatus.ACTIVE,
                ChannelUtils.parseChannel(request.getChannel())
        );

        // Check if TIME_WINDOW rule is enabled in DB
        BigDecimal enabled = RuleUtils.getRuleValue(rules, RuleType.TIME_WINDOW);

        // If TIME_WINDOW rule not present => ignore
        if (enabled == null) {
            return ValidationResult.allowed();
        }

        LocalTime now = LocalTime.now();
        LocalTime start = LocalTime.of(startHour, 0);
        LocalTime end = LocalTime.of(endHour, 0);

        boolean allowed;

        // Normal case: 9 -> 18
        if (start.isBefore(end) || start.equals(end)) {
            allowed = !now.isBefore(start) && !now.isAfter(end);
        } else {
            // Overnight case: 22 -> 6
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
