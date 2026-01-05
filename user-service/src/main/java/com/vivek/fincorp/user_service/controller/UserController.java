package com.vivek.fincorp.user_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vivek.fincorp.user_service.dto.UpdateUserProfileRequest;
import com.vivek.fincorp.user_service.dto.UserProfileResponse;
import com.vivek.fincorp.user_service.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public UserProfileResponse getMyProfile(@RequestHeader("X-USER-ID") String userId){
        return userService.getProfile(userId);
    }

    @PutMapping
    public UserProfileResponse updateMyProfile(@RequestHeader("X-USER-ID") String userId, @RequestBody @Valid UpdateUserProfileRequest request){
        return userService.updateProfile(userId, request);
    }
    
}
