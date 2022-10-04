package br.com.app.bank.manager.app.controller.v1

import br.com.app.bank.manager.app.controller.v1.response.CustomResponseError
import br.com.app.bank.manager.app.controller.v1.response.ValidationResponseError
import br.com.app.bank.manager.core.exception.AccountNotFoundException
import br.com.app.bank.manager.domain.exception.CustomException
import br.com.app.bank.manager.core.exception.ValidationException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ControllerAdvice {

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun exception(ex: Exception): CustomResponseError {
        ex.printStackTrace()
        return CustomResponseError("Internal Server Error")
    }

    @ExceptionHandler(CustomException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun customException(ex: CustomException) = CustomResponseError(ex.message)

    @ExceptionHandler(AccountNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun accountNotFoundException(ex: CustomException) = CustomResponseError(ex.message)

    @ExceptionHandler(ValidationException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun validationException(ex: ValidationException) = ValidationResponseError(ex.message, ex.fields)
}