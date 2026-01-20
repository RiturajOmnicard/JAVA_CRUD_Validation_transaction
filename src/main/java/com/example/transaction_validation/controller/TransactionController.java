package com.example.transaction_validation.controller;

import com.example.transaction_validation.dto.request.TransactionRequestDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

//    private final TransactionValidationService transactionValidationService;

//    public TransactionController(TransactionValidationService transactionValidationService) {
//        this.transactionValidationService = transactionValidationService;
//    }

//    @PostMapping("/validate")
//    public ResponseEntity<TransactionResponseDTO> validateTransaction(@RequestBody TransactionRequestDTO request) {
//        TransactionResponseDTO response = transactionValidationService.validateTransaction(request);
//        return ResponseEntity.ok(response);
//    }

    @GetMapping("/user/{userId}")
    public void getUserTransactions(@PathVariable String userId) {
//        List<TransactionLog> logs = transactionValidationService.getUserTransactions(userId);
//        return ResponseEntity.ok(logs);
        System.out.println("getting user transactions");
    }
}
