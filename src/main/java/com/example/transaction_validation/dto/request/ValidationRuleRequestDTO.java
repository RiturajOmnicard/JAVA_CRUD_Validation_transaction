package com.example.transaction_validation.dto.request;

import com.example.transaction_validation.constants.Channel;
import com.example.transaction_validation.constants.RuleStatus;
import com.example.transaction_validation.constants.RuleType;

import java.math.BigDecimal;

public class ValidationRuleRequestDTO {

    private RuleType ruleType;
    private BigDecimal ruleValue;
    private Channel channel;
    private RuleStatus status;

    public ValidationRuleRequestDTO() {}

    public ValidationRuleRequestDTO(RuleType ruleType, BigDecimal ruleValue, Channel channel, RuleStatus status) {
        this.ruleType = ruleType;
        this.ruleValue = ruleValue;
        this.channel = channel;
        this.status = status;
    }

    public RuleType getRuleType() {
        return ruleType;
    }

    public void setRuleType(RuleType ruleType) {
        this.ruleType = ruleType;
    }

    public BigDecimal getRuleValue() {
        return ruleValue;
    }

    public void setRuleValue(BigDecimal ruleValue) {
        this.ruleValue = ruleValue;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public RuleStatus getStatus() {
        return status;
    }

    public void setStatus(RuleStatus status) {
        this.status = status;
    }
}
