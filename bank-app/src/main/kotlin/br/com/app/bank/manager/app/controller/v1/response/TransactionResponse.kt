package br.com.app.bank.manager.app.controller.v1.response

import java.time.ZonedDateTime
import java.util.UUID

interface TransactionResponse{
    val id: UUID
    val operation: String
    val amount: Double
    val date: ZonedDateTime
}