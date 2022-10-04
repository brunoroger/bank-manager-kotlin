package br.com.app.bank.manager.core.command

import org.hibernate.validator.constraints.br.CPF
import javax.validation.constraints.DecimalMin
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

class TransferAccountCommand private constructor(
    val documentFrom: String,
    val documentTo: String,
    val amount: Double
) {
    data class Builder(
        @field:[NotNull CPF]
        val documentFrom: String?,

        @field:[NotNull CPF]
        val documentTo: String?,

        @field:[NotNull DecimalMin("0.01")]
        val amount: Double?
    ):BuildCommand<TransferAccountCommand>() {
        override fun mapToCommand() = TransferAccountCommand(
            documentFrom = documentFrom!!,
            documentTo = documentTo!!,
            amount = amount!!
        )
    }
}