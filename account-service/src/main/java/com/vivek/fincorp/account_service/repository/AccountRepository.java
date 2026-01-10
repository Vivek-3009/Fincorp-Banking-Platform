package com.vivek.fincorp.account_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vivek.fincorp.account_service.entity.Account;

public interface AccountRepository extends JpaRepository<Account,String>{

    
}