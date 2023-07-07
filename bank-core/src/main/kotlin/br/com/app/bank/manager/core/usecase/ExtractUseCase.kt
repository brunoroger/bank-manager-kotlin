package br.com.app.bank.manager.core.usecase

import br.com.app.bank.manager.core.command.ExtractCommand
import br.com.app.bank.manager.core.exception.AccountNotFoundException
import br.com.app.bank.manager.core.adapters.AccountPersistenceAdapter
import br.com.app.bank.manager.core.adapters.TransactionPersistenceAdapter
import br.com.app.bank.manager.domain.Transaction

class ExtractUseCase(
    private val transactionPersistenceAdapter: TransactionPersistenceAdapter,
    private val accountPersistenceAdapter: AccountPersistenceAdapter
    ) {
    fun execute(command: ExtractCommand): List<Transaction> {
        accountPersistenceAdapter.findByDocument(command.document)?: throw AccountNotFoundException(command.document)

        return transactionPersistenceAdapter.findByDocument(command.document)
    }
}