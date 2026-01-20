package com.example.transaction_validation.validation.rules;

import com.example.transaction_validation.dto.request.TransactionLogRequestDTO;
import com.example.transaction_validation.validation.RuleValidator;
import com.example.transaction_validation.validation.ValidationResult;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class BlacklistedUserRule implements RuleValidator {

    private static final Set<String> BLACKLISTED_USERS = new HashSet<>();

    static {
        BLACKLISTED_USERS.add("USR666");
    }

    @Override
    public ValidationResult validate(TransactionLogRequestDTO request) {

        if (BLACKLISTED_USERS.contains(request.getUserId())) {
            return ValidationResult.blocked(
                    "BLACKLISTED_USER",
                    "User is blacklisted and transactions are blocked."
            );
        }

        return ValidationResult.allowed();
    }
}
