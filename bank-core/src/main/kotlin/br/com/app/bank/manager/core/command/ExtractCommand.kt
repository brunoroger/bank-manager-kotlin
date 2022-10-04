package br.com.app.bank.manager.core.command

import org.hibernate.validator.constraints.br.CPF
import javax.validation.constraints.NotNull

class ExtractCommand private constructor(val document: String){

    data class Builder(
        @field:[NotNull CPF]
        val document: String?
        ): BuildCommand<ExtractCommand>() {
        override fun mapToCommand() = ExtractCommand(document = document!!)
    }
}