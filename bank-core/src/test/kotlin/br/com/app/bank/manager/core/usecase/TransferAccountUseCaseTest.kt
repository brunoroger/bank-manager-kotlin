package br.com.app.bank.manager.core.usecase

import br.com.app.bank.manager.core.command.TransferAccountCommand
import br.com.app.bank.manager.core.exception.AccountNotFoundException
import br.com.app.bank.manager.core.exception.TransferToSameDocumentException
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

class TransferAccountUseCaseTest{

    @MockK
    private lateinit var accountRepository: AccountRepository

    @InjectMockKs
    private lateinit var transferAccountUseCase: TransferAccountUseCase

    private val accountFrom = Account("84091324940", 10.0)
    private val accountTo = Account("61382162960", 0.0)

    private val command = TransferAccountCommand.Builder(
        documentFrom = accountFrom.document,
        documentTo = accountTo.document,
        amount = accountFrom.balance
    ).toCommand()

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `should transfer account`(){
        every { accountRepository.findByDocument(accountFrom.document) } returns accountFrom
        every { accountRepository.findByDocument(accountTo.document) } returns accountTo
        every { accountRepository.saveAll(any()) } returns Unit

        transferAccountUseCase.execute(command)

        verify { accountRepository.saveAll(withArg {
            Assertions.assertEquals(2, it.size)
            val accFrom = it[0]
            val accTo = it[1]

            Assertions.assertEquals(Operation.WITHDRAW, accFrom.listTransaction[0].operation)
            Assertions.assertEquals(accFrom.listTransaction[0].documentParent, accTo.document)
            Assertions.assertEquals(Operation.DEPOSIT, accTo.listTransaction[0].operation)
            Assertions.assertEquals(accTo.listTransaction[0].documentParent, accFrom.document)
        }) }
    }

    @Test
    fun `should throw exception with some documents`(){
        every { accountRepository.findByDocument(accountFrom.document) } returns accountFrom

        Assertions.assertThrows(TransferToSameDocumentException::class.java){
            transferAccountUseCase.execute(TransferAccountCommand.Builder(
                documentFrom = accountFrom.document,
                documentTo = accountFrom.document,
                amount = accountFrom.balance
            ).toCommand())
        }
    }

    @Test
    fun `should not found document from`(){
        every { accountRepository.findByDocument(accountFrom.document) } returns null

        Assertions.assertThrows(AccountNotFoundException::class.java){
            transferAccountUseCase.execute(command)
        }
    }

    @Test
    fun `should not found document to`(){
        every { accountRepository.findByDocument(accountFrom.document) } returns accountFrom
        every { accountRepository.findByDocument(accountTo.document) } returns null

        Assertions.assertThrows(AccountNotFoundException::class.java){
            transferAccountUseCase.execute(command)
        }
    }
}