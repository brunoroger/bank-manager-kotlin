package br.com.app.bank.manager.domain

import br.com.app.bank.manager.domain.enums.Operation
import java.time.ZonedDateTime
import java.util.UUID

data class Transaction(
    val id: UUID = UUID.randomUUID(),
    val document: String,
    val documentParent: String? = null,
    val operation: Operation,
    val amount: Double,
    val date: ZonedDateTime = ZonedDateTime.now()
)
