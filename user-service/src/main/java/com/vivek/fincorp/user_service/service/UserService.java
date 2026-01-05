package com.vivek.fincorp.user_service.service;

import org.springframework.stereotype.Service;

import com.vivek.fincorp.user_service.dto.UpdateUserProfileRequest;
import com.vivek.fincorp.user_service.dto.UserProfileResponse;
import com.vivek.fincorp.user_service.entity.UserProfile;
import com.vivek.fincorp.user_service.mapper.UserProfileMapper;
import com.vivek.fincorp.user_service.repository.UserProfileRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserProfileRepository userProfileRepository;

    public UserProfileResponse getProfile(String userId){
        UserProfile user = userProfileRepository.findById(userId).orElse(null);
        return UserProfileMapper.toResponse(user);
    }

    public UserProfileResponse updateProfile(String userId, UpdateUserProfileRequest request){
        UserProfile user = userProfileRepository.findById(userId).orElse(new UserProfile(userId,null,null,null));
        UserProfileMapper.toEntity(user, request);
        return UserProfileMapper.toResponse(user);
    }
}
