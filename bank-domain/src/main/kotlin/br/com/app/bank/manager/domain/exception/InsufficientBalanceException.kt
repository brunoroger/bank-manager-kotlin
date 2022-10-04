package br.com.app.bank.manager.domain.exception

class InsufficientBalanceException(val document: String): CustomException("Insufficient balance for account with document $document")