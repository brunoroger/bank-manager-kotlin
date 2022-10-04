package br.com.app.bank.manager.core.command

import br.com.app.bank.manager.core.exception.ValidationException
import br.com.app.bank.manager.core.exception.validation.FieldRule
import javax.validation.ConstraintViolation
import javax.validation.Validation
import javax.validation.ValidatorFactory


abstract class BuildCommand<C> {

    protected abstract fun mapToCommand(): C

    private fun <T> validate(clazz: T){
        val factory: ValidatorFactory = Validation.buildDefaultValidatorFactory()

        val validator = factory.validator

        val constraintViolations: Set<ConstraintViolation<T>> = validator.validate(clazz)

        if (constraintViolations.isNotEmpty()) {
            throw ValidationException(constraintViolations.map {
                FieldRule(field = it.propertyPath.toString(), rule = it.message)
            })
        }
    }

    fun toCommand(): C {
        validate(this)
        return mapToCommand()
    }
}