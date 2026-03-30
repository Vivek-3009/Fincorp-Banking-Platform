package com.vivek.fincorp.transaction_service.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vivek.fincorp.transaction_service.dto.DepositRequest;
import com.vivek.fincorp.transaction_service.dto.TransactionResponse;
import com.vivek.fincorp.transaction_service.dto.TransferRequest;
import com.vivek.fincorp.transaction_service.dto.WithdrawRequest;

public interface TransactionService {

    TransactionResponse transfer(String userId, String idempotencyKey, TransferRequest request);
    Page<TransactionResponse> getTransactionsByAccount(String userId, String accountNumber,Pageable pageable);
    TransactionResponse getTransactionById(String userId, String transactionId);
    public TransactionResponse withdraw(String userId,String idempotencyKey,WithdrawRequest request);
    public TransactionResponse deposit(String userId, String idempotencyKey, DepositRequest request);
}
