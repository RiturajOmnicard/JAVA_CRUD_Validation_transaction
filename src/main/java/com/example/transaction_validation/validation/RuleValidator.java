package com.example.transaction_validation.validation;

import com.example.transaction_validation.dto.request.TransactionLogRequestDTO;
import com.example.transaction_validation.validation.ValidationResult;
import com.example.transaction_validation.dto.request.TransactionRequestDTO;

public interface RuleValidator {
    ValidationResult validate(TransactionLogRequestDTO request);
}
