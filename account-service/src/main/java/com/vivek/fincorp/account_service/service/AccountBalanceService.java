package com.vivek.fincorp.account_service.service;

import java.math.BigDecimal;

public interface AccountBalanceService {

    public void debit(String accountNumber, BigDecimal amount, String transactionId);
    public void credit(String accountNumber, BigDecimal amount, String transactionId);
}
