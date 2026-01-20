package com.example.transaction_validation.dto.response;

import com.example.transaction_validation.constants.Channel;
import com.example.transaction_validation.constants.RuleStatus;
import com.example.transaction_validation.constants.RuleType;

import java.math.BigDecimal;

public class ValidationRuleResponseDTO {

    private Long id;
    private RuleType ruleType;
    private BigDecimal ruleValue;
    private Channel channel;
    private RuleStatus status;

    public ValidationRuleResponseDTO() {}

    public ValidationRuleResponseDTO(Long id, RuleType ruleType, BigDecimal ruleValue, Channel channel, RuleStatus status) {
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
