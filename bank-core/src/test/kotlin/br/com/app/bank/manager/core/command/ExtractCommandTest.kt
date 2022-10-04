package br.com.app.bank.manager.core.command

import br.com.app.bank.manager.core.exception.ValidationException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ExtractCommandTest: CommandTest(){
    @Test
    fun `should map to command`(){
        ExtractCommand.Builder("84091324940").toCommand()
    }

    @Test
    fun `should throw exception with parameter null`(){
        val ex = Assertions.assertThrows(ValidationException::class.java){
            ExtractCommand.Builder(null).toCommand()
        }

        Assertions.assertTrue(containsInFieldValidation(ex.fields, listOf("document")))
    }

    @Test
    fun `should throw exception with invalid document`(){
        val ex = Assertions.assertThrows(ValidationException::class.java){
            ExtractCommand.Builder("12345678901").toCommand()
        }

        Assertions.assertTrue(containsInFieldValidation(ex.fields, listOf("document")))
    }
}