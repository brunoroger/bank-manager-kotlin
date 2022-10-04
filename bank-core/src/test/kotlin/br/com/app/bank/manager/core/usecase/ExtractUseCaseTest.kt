package br.com.app.bank.manager.core.usecase

import br.com.app.bank.manager.core.command.ExtractCommand
import br.com.app.bank.manager.core.exception.AccountNotFoundException
import br.com.app.bank.manager.core.repository.AccountRepository
import br.com.app.bank.manager.core.repository.TransactionRepository
import br.com.app.bank.manager.domain.Account
import br.com.app.bank.manager.domain.Transaction
import br.com.app.bank.manager.domain.enums.Operation
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ExtractUseCaseTest {

    @MockK
    private lateinit var transactionRepository: TransactionRepository

    @MockK
    private lateinit var accountRepository: AccountRepository

    @InjectMockKs
    private lateinit var extractUseCase: ExtractUseCase

    private val account = Account(
        document = "84091324940",
        balance = 0.0,
        listTransaction = listOf(
            Transaction(document = "84091324940", operation = Operation.DEPOSIT, amount = 10.0)
        )
    )

    private val extractCommand = ExtractCommand.Builder(account.document).toCommand()

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `should extract account`(){
        every { accountRepository.findByDocument(account.document) } returns account
        every { transactionRepository.findByDocument(account.document) } returns account.listTransaction

        Assertions.assertEquals(extractUseCase.execute(extractCommand), account.listTransaction)
    }

    @Test
    fun `should not found account`(){
        every { accountRepository.findByDocument(account.document) } returns null

        Assertions.assertThrows(AccountNotFoundException::class.java){
            extractUseCase.execute(extractCommand)
        }
    }
}