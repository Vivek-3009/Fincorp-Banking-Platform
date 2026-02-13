package com.vivek.fincorp.account_service.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.vivek.fincorp.account_service.dto.AccountResponse;
import com.vivek.fincorp.account_service.dto.CreateAccountRequest;
import com.vivek.fincorp.account_service.service.AccountService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
@Validated
@Tag(
    name = "Account APIs",
    description = "APIs for managing user bank accounts"
)
public class AccountController {

    private final AccountService accountService;

    @Operation(
        summary = "Create a new bank account",
        description = "Creates a new bank account for the authenticated user",
        responses = {
            @ApiResponse(
                responseCode = "201",description = "Account created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
        }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponse createAccount(@RequestHeader("X-USER-ID") String userId, @Valid @RequestBody CreateAccountRequest request) {
        return accountService.createAccount(userId, request);
    }


    @Operation(
        summary = "Get all user accounts",
        description = "Fetches all bank accounts owned by the authenticated user",
        responses = {
            @ApiResponse(responseCode = "200",description = "Accounts fetched successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
        }
    )
    @GetMapping
    public List<AccountResponse> getAccountsByUser(@RequestHeader("X-USER-ID") String userId) {

        return accountService.getAccountsByUser(userId);
    }


    @Operation(
        summary = "Get account by account number",
        description = "Fetches a specific bank account owned by the authenticated user",
        responses = {
            @ApiResponse(responseCode = "200", description = "Account fetched successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "404", description = "Account not found")
        }
    )
    @GetMapping("/{accountNumber}")
    public AccountResponse getAccountByNumber(@RequestHeader("X-USER-ID") String userId, @PathVariable String accountNumber) {

        return accountService.getAccountByNumber(userId, accountNumber);
    }
}
