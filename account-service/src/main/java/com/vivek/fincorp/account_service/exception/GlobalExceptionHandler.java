package com.vivek.fincorp.account_service.exception;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(AccountNotFoundException.class)
        public ResponseEntity<ErrorResponse> handleAccountNotFound(AccountNotFoundException ex) {

                return ResponseEntity
                                .status(HttpStatus.NOT_FOUND)
                                .body(new ErrorResponse(
                                                HttpStatus.NOT_FOUND.value(),
                                                ex.getMessage()));
        }

        @ExceptionHandler(AccountAccessDeniedException.class)
        public ResponseEntity<ErrorResponse> handleAccessDenied(AccountAccessDeniedException ex) {

                return ResponseEntity
                                .status(HttpStatus.FORBIDDEN)
                                .body(new ErrorResponse(
                                                HttpStatus.FORBIDDEN.value(),
                                                ex.getMessage()));
        }

        @ExceptionHandler(InvalidAccountStateException.class)
        public ResponseEntity<ErrorResponse> handleInvalidState(InvalidAccountStateException ex) {

                return ResponseEntity
                                .badRequest()
                                .body(new ErrorResponse(
                                                HttpStatus.BAD_REQUEST.value(),
                                                ex.getMessage()));
        }

        @ExceptionHandler(InsufficientBalanceException.class)
        public ResponseEntity<ErrorResponse> handleInsufficientBalance(InsufficientBalanceException ex) {

                return ResponseEntity
                                .badRequest()
                                .body(new ErrorResponse(
                                                HttpStatus.BAD_REQUEST.value(),
                                                ex.getMessage()));
        }

        @ExceptionHandler(OptimisticLockingFailureException.class)
        public ResponseEntity<ErrorResponse> handleOptimisticLocking(OptimisticLockingFailureException ex) {

                return ResponseEntity
                                .status(HttpStatus.CONFLICT)
                                .body(new ErrorResponse(
                                        HttpStatus.CONFLICT.value(),
                                        "Account was updated concurrently. Please retry."
                                ));
        }
}
