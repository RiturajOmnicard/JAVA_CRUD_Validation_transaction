package com.example.transaction_validation.controller;

import com.example.transaction_validation.dto.request.TransactionLogRequestDTO;
import com.example.transaction_validation.dto.response.TransactionLogResponseDTO;
import com.example.transaction_validation.entity.TransactionLog;
import com.example.transaction_validation.service.TransactionValidationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionValidationService transactionValidationService;

    public TransactionController(TransactionValidationService transactionValidationService) {
        this.transactionValidationService = transactionValidationService;
    }

    @PostMapping("/validate")
    public ResponseEntity<TransactionLogResponseDTO> validateTransaction(@RequestBody TransactionLogRequestDTO request) {
        TransactionLogResponseDTO response = transactionValidationService.validateTransaction(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TransactionLogResponseDTO>> getUserTransactions(@PathVariable String userId) {
        List<TransactionLog> logs = transactionValidationService.getUserTransactions(userId);

        List<TransactionLogResponseDTO> responseList = new ArrayList<>();

        for (TransactionLog log : logs) {
            TransactionLogResponseDTO dto = new TransactionLogResponseDTO();
            dto.setStatus(log.getStatus());

            // optional: set error as null (because log stores status)
            dto.setError(null);

            responseList.add(dto);
        }

        return ResponseEntity.ok(responseList);
    }
}
