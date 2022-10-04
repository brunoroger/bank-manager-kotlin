package br.com.app.bank.manager.core.exception

import br.com.app.bank.manager.domain.exception.CustomException

class AccountDuplicatedException(private val document: String) : CustomException("Account duplicated for document $document")