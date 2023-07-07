package br.com.app.bank.manager.core.usecase

import br.com.app.bank.manager.core.command.UpdateBalanceCommand
import br.com.app.bank.manager.core.exception.AccountNotFoundException
import br.com.app.bank.manager.core.adapters.AccountPersistenceAdapter
import br.com.app.bank.manager.domain.Account
import br.com.app.bank.manager.domain.Transaction

class UpdateBalanceUseCase(private val accountPersistenceAdapter: AccountPersistenceAdapter) {
    fun execute(command: UpdateBalanceCommand){
        val account = accountPersistenceAdapter.findByDocument(command.document)
            ?: throw AccountNotFoundException(command.document)

        accountPersistenceAdapter.save(Account(
            document = account.document,
            balance = account.balance,
            listTransaction = listOf(
                Transaction(
                    document = account.document,
                    operation = command.operation,
                    amount = command.amount
                )
            )
        ))
    }
}