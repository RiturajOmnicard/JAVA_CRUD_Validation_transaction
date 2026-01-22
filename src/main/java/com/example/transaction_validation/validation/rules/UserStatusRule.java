package com.example.transaction_validation.validation.rules;

import com.example.transaction_validation.dto.request.TransactionLogRequestDTO;
import com.example.transaction_validation.entity.User;
import com.example.transaction_validation.repository.UserRepository;
import com.example.transaction_validation.validation.RuleValidator;
import com.example.transaction_validation.validation.ValidationResult;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserStatusRule implements RuleValidator {

    private final UserRepository userRepository;

    public UserStatusRule(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ValidationResult validate(TransactionLogRequestDTO request) {

        Optional<User> userOpt = userRepository.findById(request.getUserId());

        if (userOpt.isPresent()) {
            String status = userOpt.get().getStatus();

            if ("INACTIVE".equalsIgnoreCase(status)) {
                return ValidationResult.blocked(
                        "USER_INACTIVE",
                        "User is inactive or blocked."
                );
            }
        }

        return ValidationResult.allowed();
    }
}
