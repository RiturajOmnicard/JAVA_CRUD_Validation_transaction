package com.example.transaction_validation.validation;

public class ValidationResult {

    private boolean allowed;
    private String errorCode;
    private String errorMessage;

    private ValidationResult(boolean allowed, String errorCode, String errorMessage) {
        this.allowed = allowed;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public static ValidationResult allowed() {
        return new ValidationResult(true, null, null);
    }

    public static ValidationResult blocked(String errorCode, String errorMessage) {
        return new ValidationResult(false, errorCode, errorMessage);
    }

    public boolean isAllowed() {
        return allowed;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
