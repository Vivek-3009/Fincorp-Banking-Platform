package com.vivek.fincorp.transaction_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vivek.fincorp.transaction_service.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, String> {

    Optional<Transaction> findByIdempotencyKey(String idempotencyKey);
}
