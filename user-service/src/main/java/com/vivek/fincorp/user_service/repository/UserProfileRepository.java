package com.vivek.fincorp.user_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vivek.fincorp.user_service.entity.UserProfile;

public interface UserProfileRepository extends JpaRepository<UserProfile, String> {
}