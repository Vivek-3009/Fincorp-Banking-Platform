package com.vivek.fincorp.transaction_service.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.vivek.fincorp.transaction_service.enums.TransactionStatus;
import com.vivek.fincorp.transaction_service.enums.TransactionType;

public record TransactionCompletedEvent(

        String transactionId,
        String userId,
        String fromAccountNumber,
        String toAccountNumber,
        BigDecimal amount,
        TransactionType type,
        TransactionStatus status,
        LocalDateTime occurredAt

) {}