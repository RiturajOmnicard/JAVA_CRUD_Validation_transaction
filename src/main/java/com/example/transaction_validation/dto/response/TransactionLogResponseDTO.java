package com.example.transaction_validation.dto.response;

import com.example.transaction_validation.constants.TransactionStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionLogResponseDTO {

    private Long id;
    private String userId;
    private BigDecimal amount;
    private TransactionStatus status;
    private String reasons;
    private LocalDateTime transactionTime;
    private ErrorDTO error;


    public TransactionLogResponseDTO() {}

    public TransactionLogResponseDTO(Long id, String userId, BigDecimal amount,
                                     TransactionStatus status, String reasons,
                                     LocalDateTime transactionTime) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.status = status;
        this.reasons = reasons;
        this.transactionTime = transactionTime;
    }

    public Long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public String getReasons() {
        return reasons;
    }

    public LocalDateTime getTransactionTime() {
        return transactionTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public void setReasons(String reasons) {
        this.reasons = reasons;
    }

    public void setTransactionTime(LocalDateTime transactionTime) {
        this.transactionTime = transactionTime;
    }

    public ErrorDTO getError() {
        return error;
    }

    public void setError(ErrorDTO error) {
        this.error = error;
    }
}
