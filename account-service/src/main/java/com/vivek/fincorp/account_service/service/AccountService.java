package com.vivek.fincorp.account_service.service;

import java.util.List;

import com.vivek.fincorp.account_service.dto.AccountResponse;
import com.vivek.fincorp.account_service.dto.CreateAccountRequest;

public interface AccountService {
    AccountResponse createAccount(String userId, CreateAccountRequest request);
    List<AccountResponse> getAccountsByUser(String userId);
    AccountResponse getAccountByNumber(String userId, String accountNumber);
}
