package com.vivek.fincorp.authentication_service.dto;

public record RegisterRequest(
    @Email String email,
    @NotBlank String password
) {}