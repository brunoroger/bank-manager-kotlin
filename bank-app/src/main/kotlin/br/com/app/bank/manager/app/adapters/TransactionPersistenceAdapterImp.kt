package br.com.app.bank.manager.app.adapters

import br.com.app.bank.manager.app.adapters.jpa.TransactionJPARepository
import br.com.app.bank.manager.core.adapters.TransactionPersistenceAdapter
import br.com.app.bank.manager.domain.Transaction
import br.com.app.bank.manager.domain.enums.Operation
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class TransactionPersistenceAdapterImp(private val transactionJPARepository: TransactionJPARepository): TransactionPersistenceAdapter {
    override fun findByDocument(document: String) =
        transactionJPARepository.findByDocument(document).map {
            Transaction(
                id = UUID.fromString(it.id),
                document = it.accountEntity.document,
                documentParent = it.documentParent,
                operation = Operation.valueOf(it.operation),
                amount = it.amount,
                date = it.date
            )
        }
}