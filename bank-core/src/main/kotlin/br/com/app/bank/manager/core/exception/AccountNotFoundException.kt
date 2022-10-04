package br.com.app.bank.manager.core.exception

import br.com.app.bank.manager.domain.exception.CustomException

class AccountNotFoundException(private val document: String): CustomException("Account not found with document $document")