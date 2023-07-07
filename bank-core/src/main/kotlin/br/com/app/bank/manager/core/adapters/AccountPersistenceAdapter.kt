package br.com.app.bank.manager.core.adapters

import br.com.app.bank.manager.domain.Account

interface AccountPersistenceAdapter {
    fun findByDocument(document: String): Account?
    fun save(account: Account)

    fun saveAll(accounts: List<Account>)
}