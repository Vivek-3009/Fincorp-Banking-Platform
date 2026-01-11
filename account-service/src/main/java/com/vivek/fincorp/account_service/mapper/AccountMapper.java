package com.vivek.fincorp.account_service.mapper;

import com.vivek.fincorp.account_service.dto.AccountResponse;
import com.vivek.fincorp.account_service.entity.Account;

public class AccountMapper {
    private AccountMapper() {
    }

    public static AccountResponse toResponse(Account account) {
        return new AccountResponse(
                account.getId(),
                account.getAccountNumber(),
                account.getBalance(),
                account.getStatus()
        );
    }
}
