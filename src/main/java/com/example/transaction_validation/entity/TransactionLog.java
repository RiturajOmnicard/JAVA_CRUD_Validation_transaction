package com.example.transaction_validation.entity;

import com.example.transaction_validation.constants.TransactionStatus;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction_log")
public class TransactionLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false, length = 100)
    private String userId;

    @Column(name = "amount", nullable = false, precision = 18, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TransactionStatus status;

    @Lob
    @Column(name = "reasons")
    private String reasons;

    @Column(name = "transaction_time", nullable = false)
    private LocalDateTime transactionTime;

    public TransactionLog() {
    }

    public TransactionLog(Long id, String userId, BigDecimal amount, TransactionStatus status, String reasons, LocalDateTime transactionTime) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.status = status;
        this.reasons = reasons;
        this.transactionTime = transactionTime;
    }

    @PrePersist
    public void prePersist() {
        if (this.transactionTime == null) {
            this.transactionTime = LocalDateTime.now();
        }
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
}
