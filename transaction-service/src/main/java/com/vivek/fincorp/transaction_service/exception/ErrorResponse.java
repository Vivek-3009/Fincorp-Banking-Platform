package com.vivek.fincorp.transaction_service.exception;

public record ErrorResponse(
    int status,
    String message
)
{}
