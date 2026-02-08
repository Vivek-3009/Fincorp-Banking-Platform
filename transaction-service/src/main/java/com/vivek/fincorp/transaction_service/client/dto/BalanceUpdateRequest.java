package com.vivek.fincorp.transaction_service.client.dto;

import java.math.BigDecimal;

public record BalanceUpdateRequest(
    BigDecimal amount,
    String transactionId
){}

