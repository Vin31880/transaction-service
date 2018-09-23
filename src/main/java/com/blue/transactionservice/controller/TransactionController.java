package com.blue.transactionservice.controller;

import com.blue.transactionservice.domain.Transaction;
import com.blue.transactionservice.domain.TransactionResourceIn;
import com.blue.transactionservice.exception.NoTransactionFoundException;
import com.blue.transactionservice.service.TransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class TransactionController {

    static final String BASE_URL = "/api/v1";

    static final String SAVE_TRANSACTION_URL = BASE_URL + "/transaction";

    static final String GET_TRANSACTION_BY_ACCOUNT_ID_URL = BASE_URL + "/transaction/{accountId}";

    private TransactionsService transactionsService;

    public TransactionController(@Autowired TransactionsService transactionsService) {
        this.transactionsService = transactionsService;
    }

    @GetMapping(value = GET_TRANSACTION_BY_ACCOUNT_ID_URL, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<List<Transaction>> getTransactionByAccountId(@PathVariable("accountId") Long accountId)
            throws NoTransactionFoundException {
        return ResponseEntity.ok().body(transactionsService.getTransactionByAccountId(accountId));
    }

    @PostMapping(value = SAVE_TRANSACTION_URL, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Transaction> saveTransaction(@RequestBody TransactionResourceIn transaction) {
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionsService.save(transaction));
    }
}
