package com.vivek.fincorp.account_service.exception;

public record ErrorResponse(
    int status,
    String message
)
{}
