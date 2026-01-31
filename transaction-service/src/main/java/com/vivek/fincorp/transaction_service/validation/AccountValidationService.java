package com.vivek.fincorp.transaction_service.validation;

import com.vivek.fincorp.transaction_service.client.dto.AccountDto;
import com.vivek.fincorp.transaction_service.client.enums.AccountStatus;
import com.vivek.fincorp.transaction_service.exception.InvalidAccountException;


public class AccountValidationService {

    private AccountValidationService() {}

    public static void validateAccount(AccountDto account, String context) {
        if (account.status() != AccountStatus.ACTIVE) {
            throw new InvalidAccountException(context + " account is not active");
        }
    }
}