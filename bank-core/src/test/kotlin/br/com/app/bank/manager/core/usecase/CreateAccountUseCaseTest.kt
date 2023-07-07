package br.com.app.bank.manager.core.usecase

import br.com.app.bank.manager.core.command.CreateAccountCommand
import br.com.app.bank.manager.core.exception.AccountDuplicatedException
import br.com.app.bank.manager.core.adapters.AccountPersistenceAdapter
import br.com.app.bank.manager.domain.Account
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CreateAccountUseCaseTest{

    @MockK
    private lateinit var accountPersistenceAdapter: AccountPersistenceAdapter

    @InjectMockKs
    private lateinit var createAccountUseCase: CreateAccountUseCase

    private val account = Account(document = "84091324940")

    private val createAccountCommand = CreateAccountCommand.Builder(account.document).toCommand()

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `should create account`(){
        every { accountPersistenceAdapter.findByDocument(account.document) } returns null
        every { accountPersistenceAdapter.save(any()) } returns Unit

        createAccountUseCase.execute(createAccountCommand)

        verify { accountPersistenceAdapter.save(any()) }
    }

    @Test
    fun `should not create account with document duplicated`(){
        every { accountPersistenceAdapter.findByDocument(account.document) } returns account
        every { accountPersistenceAdapter.save(any()) } returns Unit

        Assertions.assertThrows(
            AccountDuplicatedException::class.java
        ) {
            createAccountUseCase.execute(createAccountCommand)
        }
    }
}