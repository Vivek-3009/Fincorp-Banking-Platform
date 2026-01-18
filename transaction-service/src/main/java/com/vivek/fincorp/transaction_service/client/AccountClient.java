package com.vivek.fincorp.transaction_service.client;

import java.math.BigDecimal;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vivek.fincorp.transaction_service.client.dto.AccountDto;

@FeignClient(name = "account-service")
public interface AccountClient {

    @GetMapping("/accounts/{accountNumber}")
    AccountDto getAccount(@PathVariable String accountNumber);

    @PutMapping("/accounts/{accountNumber}/debit")
    void debit(@PathVariable String accountNumber, @RequestParam BigDecimal amount);

    @PutMapping("/accounts/{accountNumber}/credit")
    void credit(@PathVariable String accountNumber, @RequestParam BigDecimal amount);
}
