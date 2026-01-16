package com.vivek.fincorp.transaction_service.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TransferRequest(

        @NotBlank(message = "Source account number is required")
        String fromAccountNumber,

        @NotBlank(message = "Destination account number is required")
        String toAccountNumber,

        @NotNull(message = "Transfer amount is required")
        @DecimalMin(value = "0.01", message = "Amount must be greater than zero")
        BigDecimal amount
) {}