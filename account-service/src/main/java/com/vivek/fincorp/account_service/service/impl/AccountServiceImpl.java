package com.vivek.fincorp.account_service.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vivek.fincorp.account_service.dto.AccountInternalDto;
import com.vivek.fincorp.account_service.dto.AccountResponse;
import com.vivek.fincorp.account_service.dto.CreateAccountRequest;
import com.vivek.fincorp.account_service.entity.Account;
import com.vivek.fincorp.account_service.enums.AccountStatus;
import com.vivek.fincorp.account_service.exception.AccountAccessDeniedException;
import com.vivek.fincorp.account_service.exception.AccountNotFoundException;
import com.vivek.fincorp.account_service.exception.InvalidAccountStateException;
import com.vivek.fincorp.account_service.mapper.AccountMapper;
import com.vivek.fincorp.account_service.repository.AccountRepository;
import com.vivek.fincorp.account_service.service.AccountService;
import com.vivek.fincorp.account_service.utils.AccountNumberGenerator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountNumberGenerator accountNumberGenerator;

    @Override
    @Transactional
    public AccountResponse createAccount(String userId, CreateAccountRequest request) {

        Account account = Account.builder()
                .userId(userId)
                .accountNumber(accountNumberGenerator.generate())
                .accountType(request.accountType())
                .balance(BigDecimal.ZERO)
                .status(AccountStatus.ACTIVE)
                .build();

        Account savedAccount = accountRepository.save(account);

        return AccountMapper.toResponse(savedAccount);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountResponse> getAccountsByUser(String userId) {

        return accountRepository.findByUserId(userId)
                .stream()
                .map(AccountMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public AccountResponse getAccountByNumber(String userId, String accountNumber) {

        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        if (!account.getUserId().equals(userId)) {
            throw new AccountAccessDeniedException("You cannot access this account");
        }

        if (account.getStatus() != AccountStatus.ACTIVE) {
            throw new InvalidAccountStateException(
                    "Account is not active: " + account.getStatus());
        }

        return AccountMapper.toResponse(account);
    }

    @Override
    public AccountInternalDto getAccountInternal(String accountNumber){
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        return new AccountInternalDto(
                account.getAccountNumber(),
                account.getUserId(),
                account.getBalance(),
                account.getStatus().toString()
        );
    }
}
