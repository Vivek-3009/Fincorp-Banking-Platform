package com.vivek.fincorp.authentication_service.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vivek.fincorp.authentication_service.dto.AuthResponse;
import com.vivek.fincorp.authentication_service.dto.LoginRequest;
import com.vivek.fincorp.authentication_service.dto.RegisterRequest;
import com.vivek.fincorp.authentication_service.entity.UserCredential;
import com.vivek.fincorp.authentication_service.exception.EmailAlreadyExistsException;
import com.vivek.fincorp.authentication_service.exception.InvalidCredentialsException;
import com.vivek.fincorp.authentication_service.repository.UserCredentialRepository;
import com.vivek.fincorp.authentication_service.security.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserCredentialRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public void register(RegisterRequest request) {
        repository.findByEmail(request.email())
                .ifPresent(u -> {
                    throw new EmailAlreadyExistsException("Email already exists");
                });

        UserCredential user = UserCredential.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role("USER")
                .enabled(true)
                .build();

        repository.save(user);
    }

    public AuthResponse login(LoginRequest request) {
        UserCredential user = repository.findByEmail(request.email())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid credentials"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user.getId(), user.getRole());
        return new AuthResponse(token);
    }
}