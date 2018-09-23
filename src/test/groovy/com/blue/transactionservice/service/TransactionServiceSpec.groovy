package com.blue.transactionservice.service

import com.blue.transactionservice.domain.Transaction
import com.blue.transactionservice.exception.NoTransactionFoundException
import com.blue.transactionservice.repository.TransactionRepository
import spock.lang.Specification

class TransactionServiceSpec extends Specification {

    TransactionRepository transactionRepository
    TransactionsService transactionsService

    void setup() {
        transactionRepository = Mock(TransactionRepository)
        transactionsService = new TransactionsService(transactionRepository)
    }

    def "it should throw exception if does not get transaction for account id"() {
        given:
        def accountId = 1L
        transactionRepository.findAllByAccountId(accountId) >> Optional.empty()

        when:
        transactionsService.getTransactionByAccountId(accountId)

        then:
        thrown(NoTransactionFoundException)
    }

    def "it should return list of transaction if exists, when getTransactionByAccountId is called"() {
        given:
        def accountId = 1L
        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(new Transaction(transactionId: 1L, accountId: 1L))
        transactionList.add(new Transaction(transactionId: 2L, accountId: 1L))

        transactionRepository.findAllByAccountId(accountId) >> Optional.of(transactionList)

        when:
        def result = transactionsService.getTransactionByAccountId(accountId)

        then:
        result == transactionList
    }

    def "it should call save method on repository while saving transaction"() {
        given:
        def transaction = new Transaction(transactionId: 1L)

        when:
        transactionsService.save(transaction)

        then:
        1 * transactionRepository.save(transaction)
    }
}
