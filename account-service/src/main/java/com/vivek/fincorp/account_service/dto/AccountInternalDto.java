package com.vivek.fincorp.account_service.dto;

import java.math.BigDecimal;


public record AccountInternalDto(
        String accountNumber,
        String userId,
        BigDecimal balance,
        String status
) {}
