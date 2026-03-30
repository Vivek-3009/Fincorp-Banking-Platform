package com.vivek.fincorp.user_service.service;

import com.vivek.fincorp.user_service.dto.UpdateUserProfileRequest;
import com.vivek.fincorp.user_service.dto.UserProfileResponse;

public interface UserService {
    public UserProfileResponse getProfile(String userId);
    public UserProfileResponse updateProfile(String userId, UpdateUserProfileRequest request);
}