package com.vivek.fincorp.transaction_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.vivek.fincorp.transaction_service.client.dto.AccountDto;
import com.vivek.fincorp.transaction_service.client.dto.BalanceUpdateRequest;

@FeignClient(name = "account-service")
public interface AccountClient {

    @GetMapping("/internal/accounts/{accountNumber}")
    AccountDto getAccount(@PathVariable String accountNumber);

    @PutMapping("/internal/accounts/{accountNumber}/debit")
    void debit(@PathVariable String accountNumber, @RequestBody BalanceUpdateRequest request);

    @PutMapping("/internal/accounts/{accountNumber}/credit")
    void credit(@PathVariable String accountNumber, @RequestBody BalanceUpdateRequest request);
}
