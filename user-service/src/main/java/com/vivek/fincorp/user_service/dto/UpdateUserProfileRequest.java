package com.vivek.fincorp.user_service.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateUserProfileRequest(
        @NotBlank String fullName,
        String phone,
        String address
) {}