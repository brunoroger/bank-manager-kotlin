package br.com.app.bank.manager.core.command

import br.com.app.bank.manager.core.exception.ValidationException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class TransferAccountCommandTest: CommandTest() {
    @Test
    fun `should throw exception with null parameter`(){
        val ex = Assertions.assertThrows(ValidationException::class.java){
            TransferAccountCommand.Builder(null, null, null).toCommand()
        }

        Assertions.assertTrue(containsInFieldValidation(ex.fields, listOf(
            "documentFrom",
            "documentTo",
            "amount"
        )))
    }

    @Test
    fun `should throw exception with empty parameter and zero amount`(){
        val ex = Assertions.assertThrows(ValidationException::class.java){
            TransferAccountCommand.Builder("", "", 0.0).toCommand()
        }

        Assertions.assertTrue(containsInFieldValidation(ex.fields, listOf(
            "documentFrom",
            "documentTo",
            "amount"
        )))
    }

    @Test
    fun `should throw exception with invalid document`(){
        val ex = Assertions.assertThrows(ValidationException::class.java){
            TransferAccountCommand.Builder("12345678901", "09876543210", 10.0).toCommand()
        }

        Assertions.assertTrue(containsInFieldValidation(ex.fields, listOf(
            "documentFrom",
            "documentTo"
        )))
    }
}