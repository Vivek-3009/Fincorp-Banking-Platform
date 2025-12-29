package com.vivek.fincorp.authentication_service.dto;

public record LoginRequest(
    @Email String email,
    @NotBlank String password
) {}