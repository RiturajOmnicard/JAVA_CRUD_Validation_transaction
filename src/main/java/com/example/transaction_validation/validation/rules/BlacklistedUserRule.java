package com.example.transaction_validation.validation.rules;

import com.example.transaction_validation.dto.request.TransactionLogRequestDTO;
import com.example.transaction_validation.entity.User;
import com.example.transaction_validation.repository.UserRepository;
import com.example.transaction_validation.validation.RuleValidator;
import com.example.transaction_validation.validation.ValidationResult;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BlacklistedUserRule implements RuleValidator {

    private final UserRepository userRepository;

    public BlacklistedUserRule(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ValidationResult validate(TransactionLogRequestDTO request) {

        Optional<User> userOpt = userRepository.findById(request.getUserId());

        if (userOpt.isPresent() && userOpt.get().isBlacklisted()) {
            return ValidationResult.blocked(
                    "BLACKLISTED_USER",
                    "User is blacklisted and transactions are blocked."
            );
        }

        return ValidationResult.allowed();
    }
}
