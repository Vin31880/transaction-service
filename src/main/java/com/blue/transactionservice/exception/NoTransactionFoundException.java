package com.blue.transactionservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoTransactionFoundException extends Exception {
    public NoTransactionFoundException() {
    }

    public NoTransactionFoundException(Long id) {
        super("No transaction found for account with id" + id);
    }
}
