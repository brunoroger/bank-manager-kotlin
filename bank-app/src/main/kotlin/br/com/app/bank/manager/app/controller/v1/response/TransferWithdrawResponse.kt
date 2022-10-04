package br.com.app.bank.manager.app.controller.v1.response

import java.time.ZonedDateTime
import java.util.UUID

data class TransferWithdrawResponse(
    override val id: UUID,
    override val operation: String,
    val documentTo: String,
    override val amount: Double,
    override val date: ZonedDateTime
): TransactionResponse