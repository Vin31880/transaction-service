package com.blue.transactionservice.controller

import com.blue.transactionservice.domain.Transaction
import com.blue.transactionservice.exception.NoTransactionFoundException
import com.blue.transactionservice.service.TransactionsService
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import spock.lang.Specification

import static org.springframework.http.HttpStatus.*
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

class TransactionControllerSpec extends Specification {
    ObjectMapper json
    MockMvc mvc
    TransactionsService transactionsService
    TransactionController transactionController


    void setup() {
        json = new ObjectMapper()
        transactionsService = Mock(TransactionsService)
        transactionController = new TransactionController(transactionsService)
        mvc = standaloneSetup(transactionController).build()

    }

    def "GET_TRANSACTION_BY_ACCOUNT_ID_URL throws exception if no value found"() {
        given:
        def accountId = 1L
        def request = get(TransactionController.GET_TRANSACTION_BY_ACCOUNT_ID_URL, accountId)
        request.contentType(MediaType.APPLICATION_JSON_UTF8)

        when:
        def response = mvc.perform(request).andReturn().response

        then:
        1 * transactionsService.getTransactionByAccountId(accountId) >> { throw new NoTransactionFoundException() }
        response.status == NOT_FOUND.value()
    }


    def "GET_TRANSACTION_BY_ACCOUNT_ID_URL returns response if found some value"() {
        given:
        def accountId = 2
        List<Transaction> transactionList = new ArrayList<>()
        transactionList.add(new Transaction(transactionId: 1L, accountId: 2L))
        transactionList.add(new Transaction(transactionId: 2L, accountId: 2L))

        transactionsService.getTransactionByAccountId(accountId) >> transactionList

        def request = MockMvcRequestBuilders.get(TransactionController.GET_TRANSACTION_BY_ACCOUNT_ID_URL,
                accountId)

        when:
        def response = mvc.perform(request).andReturn().response

        then:
        response.status == OK.value()
        response.contentAsString == json.writeValueAsString(transactionList)
    }

    def "save transaction"() {
        given:
        def transaction =  new Transaction(transactionId: 1L, accountId: 1L, transactionAmount: 100)

        def jsonBody = json.writeValueAsString(transaction)
        def request = MockMvcRequestBuilders.post(TransactionController.SAVE_TRANSACTION_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonBody)
        transactionsService.save(transaction) >> transaction
        when:
        def response = mvc.perform(request).andReturn().response

        then:
        response.status == CREATED.value()
    }

}
