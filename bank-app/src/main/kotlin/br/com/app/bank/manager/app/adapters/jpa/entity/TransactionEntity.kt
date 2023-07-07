package br.com.app.bank.manager.app.adapters.jpa.entity

import java.time.ZonedDateTime
import javax.persistence.Entity
import javax.persistence.Table
import javax.persistence.Id
import javax.persistence.Column
import javax.persistence.FetchType
import javax.persistence.ManyToOne
import javax.persistence.JoinColumn

@Entity
@Table(name = "transaction")
data class TransactionEntity(
    @Id
    @Column(name = "id")
    val id: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="document")
    val accountEntity: AccountEntity,

    @Column(name="document_parent")
    val documentParent: String?,

    @Column(name = "operation")
    val operation: String,

    @Column(name = "amount")
    val amount: Double,

    @Column(name = "date")
    val date: ZonedDateTime
)
