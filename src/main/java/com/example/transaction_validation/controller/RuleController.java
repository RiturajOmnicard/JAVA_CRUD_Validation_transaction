package com.example.transaction_validation.controller;

import com.example.transaction_validation.entity.ValidationRule;
import com.example.transaction_validation.service.ValidationRuleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rules")
public class RuleController {

    private final ValidationRuleService validationRuleService;

    public RuleController(ValidationRuleService validationRuleService) {
        this.validationRuleService = validationRuleService;
    }

    @PostMapping
    public ResponseEntity<ValidationRule> createOrUpdateRule(@RequestBody ValidationRule rule) {
        ValidationRule savedRule = validationRuleService.saveRule(rule);
        return ResponseEntity.ok(savedRule);
    }

    @GetMapping
    public ResponseEntity<List<ValidationRule>> getActiveRules() {
        List<ValidationRule> rules = validationRuleService.getActiveRules();
        return ResponseEntity.ok(rules);
    }
}
