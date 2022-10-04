package br.com.app.bank.manager.domain

import br.com.app.bank.manager.domain.enums.Operation
import br.com.app.bank.manager.domain.exception.InsufficientBalanceException

class Account(document: String,
              balance: Double = 0.0,
              listTransaction: List<Transaction> = emptyList()) {
    val document: String
    val balance: Double
    val listTransaction: List<Transaction>

    init {
        this.document = document

        var sum = balance
        listTransaction.forEach {
            when(it.operation){
                Operation.DEPOSIT -> sum += it.amount
                Operation.WITHDRAW -> {
                    sum -= it.amount

                    if(sum < 0.0){
                        throw InsufficientBalanceException(document)
                    }
                }
            }
        }

        this.balance = sum
        this.listTransaction = listTransaction
    }
}