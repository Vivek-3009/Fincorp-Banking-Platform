package com.vivek.fincorp.transaction_service.validation;

import com.vivek.fincorp.transaction_service.client.dto.AccountDto;
import com.vivek.fincorp.transaction_service.client.enums.AccountStatus;
import com.vivek.fincorp.transaction_service.exception.InvalidAccountException;
import com.vivek.fincorp.transaction_service.exception.UnauthorizedException;


public class AccountValidationService {

    private AccountValidationService() {}

    public static void validateAccount(AccountDto account, String context) {
        if (account.status() != AccountStatus.ACTIVE) {
            throw new InvalidAccountException(context + " account is not active");
        }
    }

    public static void validateOwnership(AccountDto account, String userId, String label) {
        if (!account.userId().equals(userId)) {
            throw new UnauthorizedException(
                    label + " account does not belong to user");
        }
    }
}