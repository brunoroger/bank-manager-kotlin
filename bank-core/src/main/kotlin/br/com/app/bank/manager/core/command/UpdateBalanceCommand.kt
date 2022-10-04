package br.com.app.bank.manager.core.command

import br.com.app.bank.manager.domain.enums.Operation
import org.hibernate.validator.constraints.br.CPF
import javax.validation.constraints.DecimalMin
import javax.validation.constraints.NotNull

class UpdateBalanceCommand private constructor(
    val document: String,
    val operation: Operation,
    val amount: Double
) {
    data class Builder(
        @field:[NotNull CPF]
        val document: String?,

        @field:NotNull
        val operation: Operation?,

        @field:[NotNull DecimalMin("0.01")]
        val amount: Double?
    ): BuildCommand<UpdateBalanceCommand>() {
        override fun mapToCommand() = UpdateBalanceCommand(
            document = document!!,
            operation = operation!!,
            amount = amount!!
        )
    }
}