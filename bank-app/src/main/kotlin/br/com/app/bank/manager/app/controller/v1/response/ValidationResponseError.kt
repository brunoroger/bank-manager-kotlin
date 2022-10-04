package br.com.app.bank.manager.app.controller.v1.response

import br.com.app.bank.manager.core.exception.validation.FieldRule

data class ValidationResponseError(val message: String, val fields: List<FieldRule>)