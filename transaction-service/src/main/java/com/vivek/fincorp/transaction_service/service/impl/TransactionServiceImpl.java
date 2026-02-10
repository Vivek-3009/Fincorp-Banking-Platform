package com.vivek.fincorp.transaction_service.service.impl;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vivek.fincorp.transaction_service.client.AccountClient;
import com.vivek.fincorp.transaction_service.client.dto.AccountDto;
import com.vivek.fincorp.transaction_service.client.dto.BalanceUpdateRequest;
import com.vivek.fincorp.transaction_service.dto.DepositRequest;
import com.vivek.fincorp.transaction_service.dto.TransactionResponse;
import com.vivek.fincorp.transaction_service.dto.TransferRequest;
import com.vivek.fincorp.transaction_service.dto.WithdrawRequest;
import com.vivek.fincorp.transaction_service.entity.Transaction;
import com.vivek.fincorp.transaction_service.enums.TransactionStatus;
import com.vivek.fincorp.transaction_service.enums.TransactionType;
import com.vivek.fincorp.transaction_service.exception.DuplicateTransactionException;
import com.vivek.fincorp.transaction_service.exception.TransactionNotFoundException;
import com.vivek.fincorp.transaction_service.mapper.TransactionMapper;
import com.vivek.fincorp.transaction_service.repository.TransactionRepository;
import com.vivek.fincorp.transaction_service.service.TransactionService;
import com.vivek.fincorp.transaction_service.validation.AccountValidationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService{

    private final TransactionRepository transactionRepository;
    private final AccountClient accountClient; 

    @Override
    @Transactional
    public TransactionResponse transfer(String userId, String idempotencyKey, TransferRequest request) {

        transactionRepository.findByIdempotencyKey(idempotencyKey)
                            .ifPresent(tx -> {
                                throw new DuplicateTransactionException("Duplicate transaction request");
                            });

        Transaction transaction = Transaction.builder()
                .id(UUID.randomUUID().toString())
                .fromAccountNumber(request.fromAccountNumber())
                .toAccountNumber(request.toAccountNumber())
                .amount(request.amount())
                .type(TransactionType.TRANSFER)
                .status(TransactionStatus.PENDING)
                .idempotencyKey(idempotencyKey)
                .build();

        transactionRepository.save(transaction);
        boolean debited = false;

        try {
            AccountDto fromAccount = accountClient.getAccount(request.fromAccountNumber());
            AccountDto toAccount = accountClient.getAccount(request.toAccountNumber());

            AccountValidationService.validateAccount(fromAccount, "Source");
            AccountValidationService.validateOwnership(fromAccount,userId,"Source");
            AccountValidationService.validateAccount(toAccount, "Destination");

            accountClient.debit(
                    request.fromAccountNumber(),
                    new BalanceUpdateRequest(
                            request.amount(),
                            transaction.getId()
                    )
            );
            debited = true;
            accountClient.credit(
                    request.toAccountNumber(),
                    new BalanceUpdateRequest(
                            request.amount(),
                            transaction.getId()
                    )
            );
            transaction.setStatus(TransactionStatus.SUCCESS);
            transactionRepository.save(transaction);

            return TransactionMapper.toResponse(transaction);

        } catch (RuntimeException ex) {

            if (debited) {
                try {
                    accountClient.credit(
                            request.fromAccountNumber(),
                            new BalanceUpdateRequest(
                                    request.amount(),
                                    transaction.getId()
                            )
                    );
                } catch (Exception compensationEx) {
                    // IMPORTANT: log & alert (manual intervention)
                }
                
            }
        
            transaction.setStatus(TransactionStatus.FAILED);
            transactionRepository.save(transaction);

            throw ex;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TransactionResponse> getTransactionsByAccount(String userId, String accountNumber, Pageable pageable) {
        AccountDto account = accountClient.getAccount(accountNumber);
        AccountValidationService.validateAccount(account, "Account");
        AccountValidationService.validateOwnership(account,userId,"Account");
        Page<Transaction> transactions = transactionRepository.findByFromAccountNumberOrToAccountNumber(accountNumber, accountNumber, pageable);

        return transactions.map(TransactionMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public TransactionResponse getTransactionById(String userId, String transactionId) {

        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() ->
                        new TransactionNotFoundException(
                                "Transaction not found"));
        
        if (transaction.getFromAccountNumber() != null) {
            AccountDto from = accountClient.getAccount(transaction.getFromAccountNumber());
            AccountValidationService.validateOwnership(from, userId, "Transaction");
        } else if (transaction.getToAccountNumber() != null) {
            AccountDto to = accountClient.getAccount(transaction.getToAccountNumber());
            AccountValidationService.validateOwnership(to, userId, "Transaction");
        }

        return TransactionMapper.toResponse(transaction);
    }
    
    @Override
    @Transactional
    public TransactionResponse withdraw(String userId,String idempotencyKey,WithdrawRequest request) {

        transactionRepository.findByIdempotencyKey(idempotencyKey).ifPresent(tx -> {
                    throw new DuplicateTransactionException("Duplicate withdraw request");
                });

        Transaction transaction = Transaction.builder()
                .id(UUID.randomUUID().toString())
                .fromAccountNumber(request.accountNumber())
                .amount(request.amount())
                .type(TransactionType.WITHDRAW)
                .status(TransactionStatus.PENDING)
                .idempotencyKey(idempotencyKey)
                .build();

        transactionRepository.save(transaction);

        try {
            AccountDto account = accountClient.getAccount(request.accountNumber());
            AccountValidationService.validateAccount(account, "Account");
            AccountValidationService.validateOwnership(account,userId,"Account");
            accountClient.debit(
                    request.accountNumber(),
                    new BalanceUpdateRequest(
                            request.amount(),
                            transaction.getId()
                    )
            );

            transaction.setStatus(TransactionStatus.SUCCESS);
            transactionRepository.save(transaction);

            return TransactionMapper.toResponse(transaction);

        } catch (RuntimeException ex) {

            transaction.setStatus(TransactionStatus.FAILED);
            transactionRepository.save(transaction);

            throw ex;
        }
    }

    @Override
    @Transactional
    public TransactionResponse deposit(String userId, String idempotencyKey, DepositRequest request) {

        transactionRepository.findByIdempotencyKey(idempotencyKey)
                .ifPresent(tx -> {
                    throw new DuplicateTransactionException(
                            "Duplicate deposit request");
                });

        Transaction transaction = Transaction.builder()
                .id(UUID.randomUUID().toString())
                .toAccountNumber(request.accountNumber())
                .amount(request.amount())
                .type(TransactionType.DEPOSIT)
                .status(TransactionStatus.PENDING)
                .idempotencyKey(idempotencyKey)
                .build();

        transactionRepository.save(transaction);

        try {
            AccountDto account = accountClient.getAccount(request.accountNumber());
            AccountValidationService.validateAccount(account, "Account");
            AccountValidationService.validateOwnership(account,userId,"Account");

            accountClient.credit(
                    request.accountNumber(),
                    new BalanceUpdateRequest(
                            request.amount(),
                            transaction.getId()
                    )
            );

            transaction.setStatus(TransactionStatus.SUCCESS);
            transactionRepository.save(transaction);

            return TransactionMapper.toResponse(transaction);

        } catch (RuntimeException ex) {

            transaction.setStatus(TransactionStatus.FAILED);
            transactionRepository.save(transaction);

            throw ex;
        }
    }


}

