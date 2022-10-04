package br.com.app.bank.manager.core.usecase

import br.com.app.bank.manager.core.command.UpdateBalanceCommand
import br.com.app.bank.manager.core.exception.AccountNotFoundException
import br.com.app.bank.manager.core.repository.AccountRepository
import br.com.app.bank.manager.domain.Account
import br.com.app.bank.manager.domain.enums.Operation
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class UpdateBalanceUseCaseTest{

    @MockK
    private lateinit var accountRepository: AccountRepository

    @InjectMockKs
    private lateinit var updateBalanceUseCase: UpdateBalanceUseCase

    private val account = Account(document = "84091324940")
    private val updateBalanceCommand = UpdateBalanceCommand.Builder(
        document = account.document,
        operation = Operation.DEPOSIT,
        amount = 10.0
    ).toCommand()

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `should update balance`(){
        every { accountRepository.findByDocument(account.document) } returns account
        every { accountRepository.save(any()) } returns Unit

        updateBalanceUseCase.execute(updateBalanceCommand)

        verify { accountRepository.save(withArg {
            Assertions.assertEquals(updateBalanceCommand.amount, it.balance)
            Assertions.assertEquals(1, it.listTransaction.size)
        }) }
    }

    @Test
    fun `should not find account`(){
        every { accountRepository.findByDocument(account.document) } returns null

        Assertions.assertThrows(AccountNotFoundException::class.java){
            updateBalanceUseCase.execute(updateBalanceCommand)
        }
    }
}