package br.com.app.bank.manager.app.adapters

import br.com.app.bank.manager.app.adapters.jpa.AccountJPARepository
import br.com.app.bank.manager.app.adapters.jpa.entity.AccountEntity
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
import java.util.Optional

class AccountPersistenceAdapterImpTest{

    @MockK
    private lateinit var accountJPARepository: AccountJPARepository

    @InjectMockKs
    private lateinit var accountRepositoryImp: AccountPersistenceAdapterImp

    private val account = Account("84091324940", 0.0, listOf(
        Transaction(
            document = "84091324940",
            operation = Operation.DEPOSIT,
            amount = 10.0
        )
    ))

    private val accountEntity = AccountEntity(
        document = account.document,
        balance = account.balance
    )

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `should find account by document`(){
        every { accountJPARepository.findById(account.document) } returns Optional.of(accountEntity)

        val account = accountRepositoryImp.findByDocument(account.document)

        Assertions.assertNotNull(account)
    }

    @Test
    fun `should not find account by document`(){
        every { accountJPARepository.findById(account.document) } returns Optional.empty()

        val account = accountRepositoryImp.findByDocument(account.document)

        Assertions.assertNull(account)
    }

    @Test
    fun `should save account`(){
        every { accountJPARepository.save(any()) } returns accountEntity

        accountRepositoryImp.save(account)

        verify { accountJPARepository.save(withArg {
            Assertions.assertEquals(1, it.listTransaction.size)
        }) }
    }

    @Test
    fun `should save list account`(){
        every { accountJPARepository.saveAll<AccountEntity>(allAny()) } returns listOf(accountEntity)

        accountRepositoryImp.saveAll(listOf(account))

        verify { accountJPARepository.saveAll<AccountEntity>(withArg {
            Assertions.assertEquals(1, it.toList()[0].listTransaction.size)
        }) }
    }
}