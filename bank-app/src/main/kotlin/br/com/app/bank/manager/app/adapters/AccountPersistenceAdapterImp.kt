package br.com.app.bank.manager.app.adapters

import br.com.app.bank.manager.app.adapters.jpa.AccountJPARepository
import br.com.app.bank.manager.app.adapters.jpa.entity.AccountEntity
import br.com.app.bank.manager.app.adapters.jpa.entity.TransactionEntity
import br.com.app.bank.manager.core.adapters.AccountPersistenceAdapter
import br.com.app.bank.manager.domain.Account
import org.springframework.stereotype.Component

@Component
class AccountPersistenceAdapterImp(private val accountJPARepository: AccountJPARepository)
    :AccountPersistenceAdapter {
    override fun findByDocument(document: String): Account? {
        val accountEntity = accountJPARepository.findById(document)

        return if(accountEntity.isPresent){
            Account(accountEntity.get().document, accountEntity.get().balance)
        }else{
            null
        }
    }

    private fun mapToAccountEntity(account: Account) = AccountEntity(
        document = account.document,
        balance = account.balance,
        listTransaction = account.listTransaction.map {
            TransactionEntity(
                id = it.id.toString(),
                accountEntity = AccountEntity(
                    document = account.document,
                    balance = account.balance
                ),
                documentParent = it.documentParent,
                operation = it.operation.toString(),
                amount = it.amount,
                date = it.date
            )
        }
    )

    override fun save(account: Account) {
        accountJPARepository.save(mapToAccountEntity(account))
    }

    override fun saveAll(accounts: List<Account>) {
        accountJPARepository.saveAll(accounts.map { mapToAccountEntity(it) })
    }
}