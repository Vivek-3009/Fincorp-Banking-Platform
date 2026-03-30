package com.vivek.fincorp.account_service.controller.internal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vivek.fincorp.account_service.dto.AccountInternalDto;
import com.vivek.fincorp.account_service.dto.BalanceUpdateRequest;
import com.vivek.fincorp.account_service.service.AccountBalanceService;
import com.vivek.fincorp.account_service.service.AccountService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/internal/accounts")
@RequiredArgsConstructor
public class InternalAccountController {

    private final AccountBalanceService accountBalanceService;
    private final AccountService accountService;

    @GetMapping("/{accountNumber}")
    public AccountInternalDto getAccountInternal(@PathVariable String accountNumber) {
        return accountService.getAccountInternal(accountNumber);
    }

    @PutMapping("/{accountNumber}/debit")
    public void debit(@PathVariable String accountNumber, @RequestBody BalanceUpdateRequest request) {
        accountBalanceService.debit(accountNumber, request.amount(), request.transactionId());
    }

    @PutMapping("/{accountNumber}/credit")
    public void credit(@PathVariable String accountNumber, @RequestBody BalanceUpdateRequest request) {
        accountBalanceService.credit(accountNumber, request.amount(), request.transactionId());
    }
}
