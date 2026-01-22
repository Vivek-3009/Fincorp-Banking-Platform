package com.vivek.fincorp.transaction_service.service.impl;

import java.util.List;

import com.vivek.fincorp.transaction_service.dto.TransactionResponse;
import com.vivek.fincorp.transaction_service.dto.TransferRequest;
import com.vivek.fincorp.transaction_service.service.TransactionService;

public class TransactionServiceImpl implements TransactionService{

    @Override
    public TransactionResponse transfer(String userId, String idempotencyKey, TransferRequest request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'transfer'");
    }

    @Override
    public List<TransactionResponse> getTransactionsByAccount(String userId, String accountNumber) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTransactionsByAccount'");
    }

    @Override
    public TransactionResponse getTransactionById(String userId, String transactionId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTransactionById'");
    }
    
}
