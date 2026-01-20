package com.example.transaction_validation.entity;

import com.example.transaction_validation.constants.RuleStatus;
import com.example.transaction_validation.constants.RuleType;
import com.example.transaction_validation.constants.Channel;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "validation_rule")
public class ValidationRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "rule_type", nullable = false)
    private RuleType ruleType;

    @Column(name = "rule_value", nullable = false, precision = 18, scale = 2)
    private BigDecimal ruleValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "channel", nullable = false)
    private Channel channel;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private RuleStatus status;

    public ValidationRule() {
    }

    public ValidationRule(Long id, RuleType ruleType, BigDecimal ruleValue, Channel channel, RuleStatus status) {
        this.id = id;
        this.ruleType = ruleType;
        this.ruleValue = ruleValue;
        this.channel = channel;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public RuleType getRuleType() {
        return ruleType;
    }

    public BigDecimal getRuleValue() {
        return ruleValue;
    }

    public Channel getChannel() {
        return channel;
    }

    public RuleStatus getStatus() {
        return status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRuleType(RuleType ruleType) {
        this.ruleType = ruleType;
    }

    public void setRuleValue(BigDecimal ruleValue) {
        this.ruleValue = ruleValue;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public void setStatus(RuleStatus status) {
        this.status = status;
    }
}
