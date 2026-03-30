package com.vivek.fincorp.account_service.dto;

import java.math.BigDecimal;

import com.vivek.fincorp.account_service.enums.AccountStatus;

public record AccountResponse(
    String accountId,
    String accountNumber,
    BigDecimal balance,
    AccountStatus status
) {}

