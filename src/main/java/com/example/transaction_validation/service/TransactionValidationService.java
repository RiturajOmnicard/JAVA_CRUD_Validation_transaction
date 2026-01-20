package com.example.transaction_validation.service;

import com.example.transaction_validation.dto.request.TransactionLogRequestDTO;
import com.example.transaction_validation.dto.response.TransactionLogResponseDTO;
import com.example.transaction_validation.entity.TransactionLog;

import java.util.List;

public interface TransactionValidationService {

    TransactionLogResponseDTO validateTransaction(TransactionLogRequestDTO request);

    List<TransactionLog> getUserTransactions(String userId);
}
