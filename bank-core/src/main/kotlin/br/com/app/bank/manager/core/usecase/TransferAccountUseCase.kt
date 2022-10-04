package br.com.app.bank.manager.core.usecase

import br.com.app.bank.manager.core.command.TransferAccountCommand
import br.com.app.bank.manager.core.exception.AccountNotFoundException
import br.com.app.bank.manager.core.exception.TransferToSameDocumentException
import br.com.app.bank.manager.core.repository.AccountRepository
import br.com.app.bank.manager.domain.Account
import br.com.app.bank.manager.domain.Transaction
import br.com.app.bank.manager.domain.enums.Operation

class TransferAccountUseCase(private val accountRepository: AccountRepository) {
    fun execute(command: TransferAccountCommand){
        if (command.documentFrom == command.documentTo){
            throw TransferToSameDocumentException(command.documentFrom)
        }

        val accountFrom = accountRepository.findByDocument(command.documentFrom)
            ?: throw AccountNotFoundException(command.documentFrom)
        val accountTo = accountRepository.findByDocument(command.documentTo)
            ?: throw AccountNotFoundException(command.documentTo)

        accountRepository.saveAll(
            listOf(
                Account(
                    document = accountFrom.document,
                    balance = accountFrom.balance,
                    listTransaction = listOf(
                        Transaction(
                            document = accountFrom.document,
                            documentParent = accountTo.document,
                            operation = Operation.WITHDRAW,
                            amount = command.amount
                        )
                    )
                ),
                Account(
                    document = accountTo.document,
                    balance = accountTo.balance,
                    listTransaction = listOf(
                        Transaction(
                            document = accountTo.document,
                            documentParent = accountFrom.document,
                            operation = Operation.DEPOSIT,
                            amount = command.amount
                        )
                    )
                )
            )
        )
    }
}