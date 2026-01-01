package com.vivek.fincorp.authentication_service.dto;

public record ErrorResponse(
        int status,
        String message
) {}
