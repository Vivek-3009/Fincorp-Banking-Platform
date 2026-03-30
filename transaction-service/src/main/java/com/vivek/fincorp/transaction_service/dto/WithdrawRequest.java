package com.vivek.fincorp.transaction_service.dto;

import java.math.BigDecimal;

public record WithdrawRequest(
        String accountNumber,
        BigDecimal amount
) {}
