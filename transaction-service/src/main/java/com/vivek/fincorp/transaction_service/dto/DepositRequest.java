package com.vivek.fincorp.transaction_service.dto;

import java.math.BigDecimal;

public record DepositRequest(
        String accountNumber,
        BigDecimal amount
) {}