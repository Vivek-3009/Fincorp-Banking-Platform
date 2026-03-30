package com.vivek.fincorp.transaction_service.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vivek.fincorp.transaction_service.dto.TransactionResponse;
import com.vivek.fincorp.transaction_service.dto.TransferRequest;
import com.vivek.fincorp.transaction_service.service.TransactionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/transfer")
    public TransactionResponse transfer(@RequestHeader("X-USER-ID") String userId, @RequestHeader("Idempotency-Key") String idempotencyKey, 
                                        @Valid @RequestBody TransferRequest request) {
        return transactionService.transfer(userId, idempotencyKey, request);
    }

    @GetMapping
    public Page<TransactionResponse> getAccountTransactions(@RequestHeader("X-USER-ID") String userId,@RequestParam String accountNumber,
            @PageableDefault(
                    page = 0,
                    size = 20,
                    sort = "createdAt",
                    direction = Sort.Direction.DESC
            )
            Pageable pageable) {

        return transactionService.getTransactionsByAccount(userId, accountNumber, pageable);
    }

    @GetMapping("/{transactionId}")
    public TransactionResponse getTransactionById(@RequestHeader("X-USER-ID") String userId, @PathVariable String transactionId) {
        return transactionService.getTransactionById(userId, transactionId);
    }
}
