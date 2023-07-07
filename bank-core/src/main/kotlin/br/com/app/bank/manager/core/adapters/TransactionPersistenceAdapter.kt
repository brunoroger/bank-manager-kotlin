package br.com.app.bank.manager.core.adapters

import br.com.app.bank.manager.domain.Transaction

interface TransactionPersistenceAdapter {
    fun findByDocument(document: String): List<Transaction>
}