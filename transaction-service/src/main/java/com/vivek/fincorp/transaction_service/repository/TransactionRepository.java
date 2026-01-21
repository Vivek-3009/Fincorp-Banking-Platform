package com.vivek.fincorp.transaction_service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vivek.fincorp.transaction_service.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, String> {

    Optional<Transaction> findByIdempotencyKey(String idempotencyKey);
    List<Transaction> findByFromAccountNumberOrToAccountNumber(String fromAccount, String toAccount);
}
