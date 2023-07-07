package br.com.app.bank.manager.core.usecase

import br.com.app.bank.manager.core.command.CreateAccountCommand
import br.com.app.bank.manager.core.exception.AccountDuplicatedException
import br.com.app.bank.manager.core.adapters.AccountPersistenceAdapter
import br.com.app.bank.manager.domain.Account

class CreateAccountUseCase(private val accountPersistenceAdapter: AccountPersistenceAdapter) {
    fun execute(command: CreateAccountCommand){
        accountPersistenceAdapter.findByDocument(command.document)?.let {
            throw AccountDuplicatedException(command.document)
        }

        accountPersistenceAdapter.save(Account(command.document))
    }
}