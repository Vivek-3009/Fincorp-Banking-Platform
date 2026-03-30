package com.vivek.fincorp.account_service.service.impl;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vivek.fincorp.account_service.entity.Account;
import com.vivek.fincorp.account_service.enums.AccountStatus;
import com.vivek.fincorp.account_service.exception.InsufficientBalanceException;
import com.vivek.fincorp.account_service.exception.InvalidAccountStateException;
import com.vivek.fincorp.account_service.repository.AccountRepository;
import com.vivek.fincorp.account_service.service.AccountBalanceService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountBalanceServiceImpl implements AccountBalanceService{

    private final AccountRepository accountRepository;

    public void debit(String accountNumber, BigDecimal amount, String transactionId) {

        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() ->
                        new IllegalArgumentException("Account not found"));

        if (account.getStatus() != AccountStatus.ACTIVE) {
            throw new InvalidAccountStateException("Account not active");
        }

        if (account.getBalance().compareTo(amount) < 0) {
            throw new InsufficientBalanceException("Insufficient balance");
        }

        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account);
    }

    public void credit(String accountNumber, BigDecimal amount, String transactionId) {

        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() ->
                        new IllegalArgumentException("Account not found"));

        if (account.getStatus() != AccountStatus.ACTIVE) {
            throw new InvalidAccountStateException("Account not active");
        }

        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);
    }
}
