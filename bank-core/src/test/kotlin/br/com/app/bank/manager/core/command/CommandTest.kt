package br.com.app.bank.manager.core.command

import br.com.app.bank.manager.core.exception.validation.FieldRule

open class CommandTest {
    protected fun containsInFieldValidation(fields: List<FieldRule>, fieldsSearch: List<String>) =
        fields.filter { fieldsSearch.contains(it.field) }.size == fieldsSearch.size
}