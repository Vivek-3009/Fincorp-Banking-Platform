package com.vivek.fincorp.user_service.dto;

public record UserProfileResponse(
        String userId,
        String fullName,
        String phone,
        String address
) {}
