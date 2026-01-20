package com.example.transaction_validation.repository;

import com.example.transaction_validation.entity.ValidationRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ValidationRuleRepository extends JpaRepository<ValidationRule, Long> {

    // Fetch all active rules
    List<ValidationRule> findByStatus(String status);

    // Fetch active rules for a particular channel (UPI/CARD/WALLET)
    List<ValidationRule> findByStatusAndChannel(String status, String channel);

    // Fetch rule by type + channel + status (useful for max/min limit)
    List<ValidationRule> findByRuleTypeAndChannelAndStatus(String ruleType, String channel, String status);
}
