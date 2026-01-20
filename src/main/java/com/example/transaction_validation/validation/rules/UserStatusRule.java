package com.example.transaction_validation.validation.rules;

import com.example.transaction_validation.dto.request.TransactionLogRequestDTO;
import com.example.transaction_validation.validation.RuleValidator;
import com.example.transaction_validation.validation.ValidationResult;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class UserStatusRule implements RuleValidator {

    private static final Set<String> INACTIVE_USERS = new HashSet<>();

    static {
        INACTIVE_USERS.add("USR999");
    }

    @Override
    public ValidationResult validate(TransactionLogRequestDTO request) {

        if (INACTIVE_USERS.contains(request.getUserId())) {
            return ValidationResult.blocked(
                    "USER_INACTIVE",
                    "User is inactive or blocked."
            );
        }

        return ValidationResult.allowed();
    }
}