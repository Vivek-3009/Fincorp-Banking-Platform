package com.vivek.fincorp.user_service.mapper;

import com.vivek.fincorp.user_service.dto.UpdateUserProfileRequest;
import com.vivek.fincorp.user_service.dto.UserProfileResponse;
import com.vivek.fincorp.user_service.entity.UserProfile;

public class UserProfileMapper {

    private UserProfileMapper(){}

    public static UserProfileResponse toResponse(UserProfile user){
        return new UserProfileResponse(
            user.getUserId(),
            user.getFullName(),
            user.getPhone(),
            user.getAddress()
        );
    }

    public static void toEntity(UserProfile user, UpdateUserProfileRequest request){
        user.setFullName(request.fullName());
        user.setPhone(request.phone());
        user.setAddress(request.address());
    }
    
}
