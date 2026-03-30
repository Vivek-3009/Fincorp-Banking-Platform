package com.vivek.fincorp.user_service.exception;

public record ErrorResponse(
    int status,
    String message
)
{}
