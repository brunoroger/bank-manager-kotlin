package br.com.app.bank.manager.app.controller.v1

import br.com.app.bank.manager.core.usecase.CreateAccountUseCase
import br.com.app.bank.manager.core.usecase.ExtractUseCase
import br.com.app.bank.manager.core.usecase.GetAccountUseCase
import br.com.app.bank.manager.core.usecase.TransferAccountUseCase
import br.com.app.bank.manager.core.usecase.UpdateBalanceUseCase
import br.com.app.bank.manager.domain.Account
import br.com.app.bank.manager.domain.Transaction
import br.com.app.bank.manager.domain.enums.Operation
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class AccountControllerTest {

    @MockK
    private lateinit var createAccountUseCase: CreateAccountUseCase

    @MockK
    private lateinit var getAccountUseCase: GetAccountUseCase

    @MockK
    private lateinit var updateBalanceUseCase: UpdateBalanceUseCase

    @MockK
    private lateinit var transferAccountUseCase: TransferAccountUseCase

    @MockK
    private lateinit var extractUseCase: ExtractUseCase

    @InjectMockKs
    private lateinit var accountController: AccountController

    private val account = Account("84091324940")
    private val accountTo = Account("61382162960")

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `should execute use case create account`(){
        every { createAccountUseCase.execute(any()) } returns Unit

        accountController.create(account.document)

        verify { createAccountUseCase.execute(any()) }
    }

    @Test
    fun `should execute use case get account`(){
        every { getAccountUseCase.execute(any()) } returns account

        val account = accountController.get(account.document)

        Assertions.assertNotNull(account)
    }

    @Test
    fun `should execute use case update balance in deposit`(){
        every { updateBalanceUseCase.execute(any()) } returns Unit

        accountController.deposit(account.document, 10.0)

        verify { updateBalanceUseCase.execute(any()) }
    }

    @Test
    fun `should execute use case update balance in withdraw`(){
        every { updateBalanceUseCase.execute(any()) } returns Unit

        accountController.withdraw(account.document, 10.0)

        verify { updateBalanceUseCase.execute(any()) }
    }

    @Test
    fun `should execute use case transfer`(){
        every { transferAccountUseCase.execute(any()) } returns Unit

        accountController.transfer(account.document, accountTo.document, 10.0)

        verify { transferAccountUseCase.execute(any()) }
    }

    @Test
    fun `should execute extract use case`(){
        every { extractUseCase.execute(any()) } returns listOf(
            Transaction(
                document = account.document,
                operation = Operation.DEPOSIT,
                amount = 10.0
            )
        )

        val listTransactions = accountController.extract(account.document)

        Assertions.assertNotNull(listTransactions)
    }
}