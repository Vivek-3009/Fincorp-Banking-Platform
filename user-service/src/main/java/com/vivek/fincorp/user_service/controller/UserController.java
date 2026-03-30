package com.vivek.fincorp.user_service.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vivek.fincorp.user_service.dto.UpdateUserProfileRequest;
import com.vivek.fincorp.user_service.dto.UserProfileResponse;
import com.vivek.fincorp.user_service.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "User", description = "User management APIs")
@Validated
public class UserController {
    private final UserService userService;

    @Operation(
        summary = "Get logged-in user profile",
        description = "Returns profile details of the authenticated user"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",description = "Profile fetched successfully",
            content = @Content(
                schema = @Schema(implementation = UserProfileResponse.class)
            )
        ),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/me")
    public UserProfileResponse getMyProfile(@RequestHeader("X-USER-ID") String userId){
        return userService.getProfile(userId);
    }
    
    @Operation(
        summary = "Update logged-in user profile",
        description = "Updates profile information of the authenticated user"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",description = "Profile updated successfully",
            content = @Content(
                schema = @Schema(implementation = UserProfileResponse.class)
            )
        ),
        @ApiResponse(responseCode = "400", description = "Validation failed"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PutMapping
    public UserProfileResponse updateMyProfile(@RequestHeader("X-USER-ID") String userId, @RequestBody @Valid UpdateUserProfileRequest request){
        return userService.updateProfile(userId, request);
    }
    
}
