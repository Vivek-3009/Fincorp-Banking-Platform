package com.vivek.fincorp.account_service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vivek.fincorp.account_service.entity.Account;

public interface AccountRepository extends JpaRepository<Account,String>{

    List<Account> findByUserId(String userID);
    Optional<Account> findByAccountNumber(String accountNumber);
}