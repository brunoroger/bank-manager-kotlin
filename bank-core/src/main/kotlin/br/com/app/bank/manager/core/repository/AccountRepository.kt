package br.com.app.bank.manager.core.repository

import br.com.app.bank.manager.domain.Account

interface AccountRepository {
    fun findByDocument(document: String): Account?
    fun save(account: Account)

    fun saveAll(accounts: List<Account>)
}