package com.blue.transactionservice.service;

import com.blue.transactionservice.domain.Transaction;
import com.blue.transactionservice.domain.TransactionResourceIn;
import com.blue.transactionservice.exception.NoTransactionFoundException;
import com.blue.transactionservice.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionsService {
    private static final Logger logger = LoggerFactory.getLogger(TransactionsService.class);

    private TransactionRepository transactionRepository;

    public TransactionsService(@Autowired TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> getTransactionByAccountId(Long accountId) throws NoTransactionFoundException {
        logger.info("Request transactions for account Id " + accountId);

        Optional<List<Transaction>> optional = transactionRepository.findAllByAccountId(accountId);
        if (!optional.isPresent()) {
            throw new NoTransactionFoundException(accountId);
        }
        return optional.get();
    }

    public Transaction save(TransactionResourceIn transaction) {
        logger.info("Saving transaction for account Id " + transaction.getAccountId());

        return transactionRepository.save(buildTransaction(transaction));
    }

    private Transaction buildTransaction(TransactionResourceIn transaction) {
        Transaction transactionToSave = new Transaction();
        transactionToSave.setAccountId(transaction.getAccountId());
        transactionToSave.setTransactionAmount(transaction.getTransactionAmount());
        transactionToSave.setTransactionType(transaction.getTransactionType());

        logger.info("Build transaction", transactionToSave);
        return transactionToSave;
    }

}
