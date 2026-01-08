package com.vivek.fincorp.account_service.dto;

import com.vivek.fincorp.account_service.enums.AccountType;

import jakarta.validation.constraints.NotNull;

public record CreateAccountRequest(
    @NotNull AccountType type
) {}
