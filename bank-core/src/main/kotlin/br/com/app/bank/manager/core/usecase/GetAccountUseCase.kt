package br.com.app.bank.manager.core.usecase

import br.com.app.bank.manager.core.command.GetAccountCommand
import br.com.app.bank.manager.core.exception.AccountNotFoundException
import br.com.app.bank.manager.core.repository.AccountRepository

class GetAccountUseCase(private val accountRepository: AccountRepository) {
    fun execute(command: GetAccountCommand) =
        accountRepository.findByDocument(command.document)
            ?: throw AccountNotFoundException(command.document)
}