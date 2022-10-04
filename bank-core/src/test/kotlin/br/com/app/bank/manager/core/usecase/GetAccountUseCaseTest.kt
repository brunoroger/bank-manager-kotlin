package br.com.app.bank.manager.core.usecase

import br.com.app.bank.manager.core.command.GetAccountCommand
import br.com.app.bank.manager.core.exception.AccountNotFoundException
import br.com.app.bank.manager.core.repository.AccountRepository
import br.com.app.bank.manager.domain.Account
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetAccountUseCaseTest{

    @MockK
    private lateinit var accountRepository: AccountRepository

    @InjectMockKs
    private lateinit var getAccountUseCase: GetAccountUseCase

    private val account = Account(document = "84091324940")

    private val getAccountCommand = GetAccountCommand.Builder(account.document).toCommand()

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `should get account`(){
        every { accountRepository.findByDocument(account.document) } returns account

        getAccountUseCase.execute(getAccountCommand)
    }

    @Test
    fun `should get account not found`(){
        every { accountRepository.findByDocument(account.document) } returns null

        Assertions.assertThrows(
            AccountNotFoundException::class.java
        ) {
            getAccountUseCase.execute(getAccountCommand)
        }
    }
}