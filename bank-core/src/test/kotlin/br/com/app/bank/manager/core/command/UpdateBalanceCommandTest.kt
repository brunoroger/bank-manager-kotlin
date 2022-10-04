package br.com.app.bank.manager.core.command

import br.com.app.bank.manager.core.exception.ValidationException
import br.com.app.bank.manager.domain.enums.Operation
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class UpdateBalanceCommandTest(): CommandTest() {
    @Test
    fun `should convert to command`(){
        UpdateBalanceCommand.Builder("84091324940", Operation.DEPOSIT, 10.0)
    }

    @Test
    fun `should throw validation exception when pass null`(){
        val ex = Assertions.assertThrows(ValidationException::class.java){
            UpdateBalanceCommand.Builder(null, null, null).toCommand()
        }

        Assertions.assertTrue(containsInFieldValidation(ex.fields, listOf(
            "document",
            "operation",
            "amount"
        )))
    }

    @Test
    fun `should throw validation exception when pass empty and amount zero`(){
        val ex = Assertions.assertThrows(ValidationException::class.java){
            UpdateBalanceCommand.Builder("", Operation.DEPOSIT, 0.0).toCommand()
        }

        Assertions.assertTrue(containsInFieldValidation(ex.fields, listOf(
            "document",
            "amount"
        )))
    }

    @Test
    fun `should throw exception with invalid document`(){
        val ex = Assertions.assertThrows(ValidationException::class.java){
            UpdateBalanceCommand.Builder("12345678901", Operation.DEPOSIT, 10.0).toCommand()
        }

        Assertions.assertTrue(containsInFieldValidation(ex.fields, listOf(
            "document"
        )))
    }
}