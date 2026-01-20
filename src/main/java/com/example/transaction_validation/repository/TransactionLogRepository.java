package com.example.transaction_validation.repository;

import com.example.transaction_validation.entity.TransactionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionLogRepository extends JpaRepository<TransactionLog, Long> {

    List<TransactionLog> findByUserId(String userId);

    List<TransactionLog> findByUserIdAndTransactionTimeBetween(
            String userId,
            LocalDateTime startTime,
            LocalDateTime endTime
    );

    long countByUserIdAndTransactionTimeBetween(
            String userId,
            LocalDateTime startTime,
            LocalDateTime endTime
    );

    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM TransactionLog t " +
            "WHERE t.userId = :userId AND t.transactionTime BETWEEN :startTime AND :endTime")
    BigDecimal sumAmountByUserIdAndTimeRange(
            @Param("userId") String userId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );

    // Get latest transaction of a user (cooling period rule)
    TransactionLog findTopByUserIdOrderByTransactionTimeDesc(String userId);
}
