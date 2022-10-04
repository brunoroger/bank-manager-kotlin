package br.com.app.bank.manager.core.repository

import br.com.app.bank.manager.domain.Transaction

interface TransactionRepository {
    fun findByDocument(document: String): List<Transaction>
}