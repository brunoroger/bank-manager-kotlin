package br.com.app.bank.manager.core.command

import br.com.app.bank.manager.core.exception.ValidationException
import br.com.app.bank.manager.core.exception.validation.FieldRule
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test


class CreateAccountCommandTest: CommandTest() {

    @Test
    fun `should convert to command`(){
        CreateAccountCommand.Builder("84091324940").toCommand()
    }

    @Test
    fun `should throw exception when document is null`(){
        val ex = Assertions.assertThrows(
            ValidationException::class.java
        ) {
            CreateAccountCommand.Builder(null).toCommand()
        }

        Assertions.assertTrue(containsInFieldValidation(ex.fields, listOf("document")))
    }

    @Test
    fun `should throw exception when document is empty`(){
        val ex = Assertions.assertThrows(
            ValidationException::class.java
        ) {
            CreateAccountCommand.Builder("").toCommand()
        }

        Assertions.assertTrue(containsInFieldValidation(ex.fields, listOf("document")))
    }
    @Test
    fun `should throw exception when document is invalid`(){
        val ex = Assertions.assertThrows(
            ValidationException::class.java
        ) {
            CreateAccountCommand.Builder("12345678901").toCommand()
        }

        Assertions.assertTrue(containsInFieldValidation(ex.fields, listOf("document")))
    }
}