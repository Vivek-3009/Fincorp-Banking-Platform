package com.vivek.fincorp.account_service.dto;

import java.math.BigDecimal;

public record BalanceUpdateRequest(
        BigDecimal amount,
        String transactionId
) {}
