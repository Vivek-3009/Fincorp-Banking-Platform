package com.vivek.fincorp.authentication_service.exception;

public record ErrorResponse(
        int status,
        String message
) {}
