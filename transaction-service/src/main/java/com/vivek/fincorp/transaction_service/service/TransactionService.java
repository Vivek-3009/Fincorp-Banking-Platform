package com.vivek.fincorp.transaction_service.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.vivek.fincorp.transaction_service.dto.TransactionResponse;
import com.vivek.fincorp.transaction_service.dto.TransferRequest;

public interface TransactionService {

    TransactionResponse transfer(String userId, String idempotencyKey, TransferRequest request);
    Page<TransactionResponse> getTransactionsByAccount(String userId, String accountNumber, );
    TransactionResponse getTransactionById(String userId, String transactionId);
}
