package com.vivek.fincorp.transaction_service.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.vivek.fincorp.transaction_service.enums.TransactionStatus;

public record TransactionResponse(

        String transactionId,
        String fromAccountNumber,
        String toAccountNumber,
        BigDecimal amount,
        TransactionStatus status,
        LocalDateTime createdAt
) {}
