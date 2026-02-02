package com.vivek.fincorp.transaction_service.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.vivek.fincorp.transaction_service.client.AccountClient;
import com.vivek.fincorp.transaction_service.client.dto.AccountDto;
import com.vivek.fincorp.transaction_service.dto.TransactionResponse;
import com.vivek.fincorp.transaction_service.dto.TransferRequest;
import com.vivek.fincorp.transaction_service.entity.Transaction;
import com.vivek.fincorp.transaction_service.enums.TransactionStatus;
import com.vivek.fincorp.transaction_service.exception.DuplicateTransactionException;
import com.vivek.fincorp.transaction_service.exception.InsufficientBalanceException;
import com.vivek.fincorp.transaction_service.exception.TransactionNotFoundException;
import com.vivek.fincorp.transaction_service.mapper.TransactionMapper;
import com.vivek.fincorp.transaction_service.repository.TransactionRepository;
import com.vivek.fincorp.transaction_service.service.TransactionService;
import com.vivek.fincorp.transaction_service.validation.AccountValidationService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService{

    private final TransactionRepository transactionRepository;
    private final AccountClient accountClient;

    @Override
    public TransactionResponse transfer(String userId, String idempotencyKey, TransferRequest request) {

        transactionRepository.findByIdempotencyKey(idempotencyKey)
                            .ifPresent(tx -> {
                                throw new DuplicateTransactionException("Duplicate transaction request");
                            });

        Transaction transaction = Transaction.builder()
                .fromAccountNumber(request.fromAccountNumber())
                .toAccountNumber(request.toAccountNumber())
                .amount(request.amount())
                .status(TransactionStatus.PENDING)
                .idempotencyKey(idempotencyKey)
                .build();

        transactionRepository.save(transaction);

        try {
            AccountDto from = accountClient.getAccount(request.fromAccountNumber());
            AccountDto to = accountClient.getAccount(request.toAccountNumber());

            AccountValidationService.validateAccount(from, "Source");
            AccountValidationService.validateAccount(to, "Destination");

            if (from.balance().compareTo(request.amount()) < 0) {
                throw new InsufficientBalanceException("Insufficient balance");
            }

            accountClient.debit(request.fromAccountNumber(), request.amount());
            accountClient.credit(request.toAccountNumber(), request.amount());

            transaction.setStatus(TransactionStatus.SUCCESS);

        } catch (RuntimeException ex) {
            transaction.setStatus(TransactionStatus.FAILED);
            throw ex;
        }

        return TransactionMapper.toResponse(transaction);
    }

    @Override
    public Page<TransactionResponse> getTransactionsByAccount(String userId, String accountNumber, Pageable pageable) {
        AccountDto account = accountClient.getAccount(accountNumber);
        AccountValidationService.validateAccount(account, "Account");

        Page<Transaction> transactions = transactionRepository.findByFromAccountNumberOrToAccountNumber(accountNumber, accountNumber, pageable);

        return transactions.map(TransactionMapper::toResponse);
    }

    @Override
    public TransactionResponse getTransactionById(String userId, String transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId).orElseThrow(() ->
                        new TransactionNotFoundException("Transaction not found"));

        return TransactionMapper.toResponse(transaction);
    }
    
}
