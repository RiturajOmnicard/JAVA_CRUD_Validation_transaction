package com.example.transaction_validation.dto.request;

import java.math.BigDecimal;

public class TransactionLogRequestDTO {

    private String userId;
    private BigDecimal amount;

    public TransactionLogRequestDTO() {}

    public TransactionLogRequestDTO(String userId, BigDecimal amount) {
        this.userId = userId;
        this.amount = amount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
