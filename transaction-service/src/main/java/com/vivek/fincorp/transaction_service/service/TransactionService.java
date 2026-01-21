package com.vivek.fincorp.transaction_service.service;

import java.util.List;

import com.vivek.fincorp.transaction_service.dto.TransactionResponse;
import com.vivek.fincorp.transaction_service.dto.TransferRequest;

public interface TransactionService {

    TransactionResponse transfer(String userId, String idempotencyKey, TransferRequest request);
    List<TransactionResponse> getTransactionsByAccount(String userId, String accountNumber);
    TransactionResponse getTransactionById(String userId, String transactionId);
}
