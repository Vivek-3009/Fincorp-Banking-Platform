package com.vivek.fincorp.transaction_service.client.dto;

import java.math.BigDecimal;

import com.vivek.fincorp.transaction_service.client.enums.AccountStatus;

public record AccountDto(
        String accountNumber,
        BigDecimal balance,
        AccountStatus status
) {}