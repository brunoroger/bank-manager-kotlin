package br.com.app.bank.manager.core.exception

import br.com.app.bank.manager.domain.exception.CustomException

class TransferToSameDocumentException(val document: String):
    CustomException("Transfer to the same document is not allowed: $document")