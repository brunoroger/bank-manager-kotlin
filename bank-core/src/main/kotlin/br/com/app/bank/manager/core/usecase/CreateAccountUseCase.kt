package br.com.app.bank.manager.core.usecase

import br.com.app.bank.manager.core.command.CreateAccountCommand
import br.com.app.bank.manager.core.exception.AccountDuplicatedException
import br.com.app.bank.manager.core.repository.AccountRepository
import br.com.app.bank.manager.domain.Account

class CreateAccountUseCase(private val accountRepository: AccountRepository) {
    fun execute(command: CreateAccountCommand){
        accountRepository.findByDocument(command.document)?.let {
            throw AccountDuplicatedException(command.document)
        }

        accountRepository.save(Account(command.document))
    }
}