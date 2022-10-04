package br.com.app.bank.manager.app.repository.jpa

import br.com.app.bank.manager.app.repository.jpa.entity.TransactionEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TransactionJPARepository: CrudRepository<TransactionEntity, String>{
    @Query("SELECT t FROM TransactionEntity t WHERE t.accountEntity.document = ?1")
    fun findByDocument(document: String): List<TransactionEntity>
}