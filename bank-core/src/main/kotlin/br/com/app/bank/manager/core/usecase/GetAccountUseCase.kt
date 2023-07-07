package br.com.app.bank.manager.core.usecase

import br.com.app.bank.manager.core.command.GetAccountCommand
import br.com.app.bank.manager.core.exception.AccountNotFoundException
import br.com.app.bank.manager.core.adapters.AccountPersistenceAdapter

class GetAccountUseCase(private val accountPersistenceAdapter: AccountPersistenceAdapter) {
    fun execute(command: GetAccountCommand) =
        accountPersistenceAdapter.findByDocument(command.document)
            ?: throw AccountNotFoundException(command.document)
}