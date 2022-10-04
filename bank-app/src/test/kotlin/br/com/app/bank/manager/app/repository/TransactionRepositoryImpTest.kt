package br.com.app.bank.manager.app.repository

import br.com.app.bank.manager.app.repository.jpa.TransactionJPARepository
import br.com.app.bank.manager.app.repository.jpa.entity.AccountEntity
import br.com.app.bank.manager.app.repository.jpa.entity.TransactionEntity
import br.com.app.bank.manager.domain.enums.Operation
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.ZonedDateTime
import java.util.UUID

class TransactionRepositoryImpTest{

    @MockK
    private lateinit var transactionJPARepository: TransactionJPARepository

    @InjectMockKs
    private lateinit var transactionRepositoryImp: TransactionRepositoryImp

    private val listTransaction = listOf(
        TransactionEntity(
            id = UUID.randomUUID().toString(),
            accountEntity = AccountEntity(
                document = "84091324940",
                balance = 10.0
            ),
            documentParent = null,
            operation = Operation.DEPOSIT.name,
            amount = 10.0,
            date = ZonedDateTime.now()
        )
    )

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `should extract transactions account by document`(){
        every { transactionJPARepository.findByDocument("84091324940") } returns listTransaction

        Assertions.assertNotNull(transactionRepositoryImp.findByDocument("84091324940"))
    }
}