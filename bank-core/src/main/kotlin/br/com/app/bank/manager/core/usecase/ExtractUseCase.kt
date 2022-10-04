package br.com.app.bank.manager.core.usecase

import br.com.app.bank.manager.core.command.ExtractCommand
import br.com.app.bank.manager.core.exception.AccountNotFoundException
import br.com.app.bank.manager.core.repository.AccountRepository
import br.com.app.bank.manager.core.repository.TransactionRepository
import br.com.app.bank.manager.domain.Transaction

class ExtractUseCase(
    private val transactionRepository: TransactionRepository,
    private val accountRepository: AccountRepository
    ) {
    fun execute(command: ExtractCommand): List<Transaction> {
        accountRepository.findByDocument(command.document)?: throw AccountNotFoundException(command.document)

        return transactionRepository.findByDocument(command.document)
    }
}