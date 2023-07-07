package br.com.app.bank.manager.app.adapters.jpa.entity

import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.Table
import javax.persistence.Id
import javax.persistence.Column
import javax.persistence.FetchType
import javax.persistence.OneToMany

@Entity
@Table(name = "account")
data class AccountEntity(

    @Id
    @Column(name = "document")
    val document: String,

    @Column(name = "balance")
    val balance: Double,

    @OneToMany(mappedBy = "accountEntity", cascade = [CascadeType.MERGE], fetch = FetchType.LAZY)
    val listTransaction: List<TransactionEntity> = emptyList()
)
