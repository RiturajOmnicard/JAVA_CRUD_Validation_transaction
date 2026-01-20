package com.example.transaction_validation.repository;

import com.example.transaction_validation.constants.Channel;
import com.example.transaction_validation.constants.RuleStatus;
import com.example.transaction_validation.constants.RuleType;
import com.example.transaction_validation.entity.ValidationRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ValidationRuleRepository extends JpaRepository<ValidationRule, Long> {

    // Fetch all active rules
    List<ValidationRule> findByStatus(RuleStatus status);

    // Fetch active rules for a particular channel (UPI/CARD/WALLET)
    List<ValidationRule> findByStatusAndChannel(RuleStatus status, Channel channel);

    // Fetch rule by type + channel + status (useful for max/min limit)
    List<ValidationRule> findByRuleTypeAndChannelAndStatus(RuleType ruleType, Channel channel, RuleStatus status);
}
