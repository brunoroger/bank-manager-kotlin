package br.com.app.bank.manager.domain

import br.com.app.bank.manager.domain.enums.Operation
import br.com.app.bank.manager.domain.exception.InsufficientBalanceException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class AccountTest {
    @Test
    fun `should deposit in account`(){
        val account = Account(
            document = "84091324940",
            listTransaction = listOf(
                Transaction(
                    document = "84091324940",
                    operation = Operation.DEPOSIT,
                    amount = 10.0
                )
            )
        )

        Assertions.assertEquals(10.0, account.balance)
    }

    @Test
    fun `should withdraw account`(){
        val account = Account(
            document = "84091324940",
            balance = 10.0,
            listTransaction = listOf(
                Transaction(
                    document = "84091324940",
                    operation = Operation.WITHDRAW,
                    amount = 10.0
                )
            )
        )

        Assertions.assertEquals(0.0, account.balance)
    }

    @Test
    fun `should not withdraw if balance is negative in account`(){
        Assertions.assertThrows(InsufficientBalanceException::class.java) {
            Account(
                document = "84091324940",
                listTransaction = listOf(
                    Transaction(
                        document = "84091324940",
                        operation = Operation.WITHDRAW,
                        amount = 10.0
                    )
                )
            )
        }
    }
}