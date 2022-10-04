package br.com.app.bank.manager.core.command

import br.com.app.bank.manager.core.exception.ValidationException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test


class GetAccountCommandTest: CommandTest() {

    @Test
    fun `should convert to command`(){
        GetAccountCommand.Builder("84091324940").toCommand()
    }

    @Test
    fun `should throw exception when document is null`(){
        val ex = Assertions.assertThrows(
            ValidationException::class.java
        ) {
            GetAccountCommand.Builder(null).toCommand()
        }

        Assertions.assertTrue(containsInFieldValidation(ex.fields, listOf("document")))
    }

    @Test
    fun `should throw exception when document is empty`(){
        val ex = Assertions.assertThrows(
            ValidationException::class.java
        ) {
            GetAccountCommand.Builder("").toCommand()
        }

        Assertions.assertTrue(containsInFieldValidation(ex.fields, listOf("document")))
    }
    @Test
    fun `should throw exception when document is invalid`(){
        val ex = Assertions.assertThrows(
            ValidationException::class.java
        ) {
            GetAccountCommand.Builder("12345678901").toCommand()
        }

        Assertions.assertTrue(containsInFieldValidation(ex.fields, listOf("document")))
    }
}