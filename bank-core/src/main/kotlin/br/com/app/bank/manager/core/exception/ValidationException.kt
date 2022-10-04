package br.com.app.bank.manager.core.exception

import br.com.app.bank.manager.core.exception.validation.FieldRule
import br.com.app.bank.manager.domain.exception.CustomException

class ValidationException(val fields: List<FieldRule>): CustomException("Invalid fields")