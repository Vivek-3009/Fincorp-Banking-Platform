package com.vivek.fincorp.account_service.exception;

public class ConcurrentAccountUpdateException extends RuntimeException {
    public ConcurrentAccountUpdateException(String message) {
        super(message);
    }
}
