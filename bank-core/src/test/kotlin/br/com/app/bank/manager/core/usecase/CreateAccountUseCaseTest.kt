package br.com.app.bank.manager.core.usecase

import br.com.app.bank.manager.core.command.CreateAccountCommand
import br.com.app.bank.manager.core.exception.AccountDuplicatedException
import br.com.app.bank.manager.core.repository.AccountRepository
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
    private lateinit var accountRepository: AccountRepository

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
        every { accountRepository.findByDocument(account.document) } returns null
        every { accountRepository.save(any()) } returns Unit

        createAccountUseCase.execute(createAccountCommand)

        verify { accountRepository.save(any()) }
    }

    @Test
    fun `should not create account with document duplicated`(){
        every { accountRepository.findByDocument(account.document) } returns account
        every { accountRepository.save(any()) } returns Unit

        Assertions.assertThrows(
            AccountDuplicatedException::class.java
        ) {
            createAccountUseCase.execute(createAccountCommand)
        }
    }
}