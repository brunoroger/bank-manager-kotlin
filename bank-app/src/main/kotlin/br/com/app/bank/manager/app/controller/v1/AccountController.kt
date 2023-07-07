package br.com.app.bank.manager.app.controller.v1

import br.com.app.bank.manager.app.controller.v1.response.AccountResponse
import br.com.app.bank.manager.app.controller.v1.response.TransactionResponseImp
import br.com.app.bank.manager.app.controller.v1.response.TransferDepositResponse
import br.com.app.bank.manager.app.controller.v1.response.TransferWithdrawResponse
import br.com.app.bank.manager.core.command.CreateAccountCommand
import br.com.app.bank.manager.core.command.ExtractCommand
import br.com.app.bank.manager.core.command.GetAccountCommand
import br.com.app.bank.manager.core.command.TransferAccountCommand
import br.com.app.bank.manager.core.command.UpdateBalanceCommand
import br.com.app.bank.manager.core.usecase.CreateAccountUseCase
import br.com.app.bank.manager.core.usecase.ExtractUseCase
import br.com.app.bank.manager.core.usecase.GetAccountUseCase
import br.com.app.bank.manager.core.usecase.TransferAccountUseCase
import br.com.app.bank.manager.core.usecase.UpdateBalanceUseCase
import br.com.app.bank.manager.domain.enums.Operation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/account")
class AccountController(
    private val createAccountUseCase: CreateAccountUseCase,
    private val getAccountUseCase: GetAccountUseCase,
    private val updateBalanceUseCase: UpdateBalanceUseCase,
    private val transferAccountUseCase: TransferAccountUseCase,
    private val extractUseCase: ExtractUseCase
    ) {

    @ApiResponses(value = [
        ApiResponse(code = 201, message = "Account created"),
        ApiResponse(code = 400, message = "Bad request"),
        ApiResponse(code = 500, message = "Internal Server Error"),
    ])
    @PostMapping("/{document}", produces=["application/json"])
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@PathVariable("document") document: String){
        createAccountUseCase.execute(CreateAccountCommand.Builder(document).toCommand())
    }

    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Account found"),
        ApiResponse(code = 404, message = "Account not found"),
        ApiResponse(code = 500, message = "Internal Server Error"),
    ])
    @GetMapping("/{document}", produces=["application/json"])
    @ResponseStatus(HttpStatus.OK)
    fun get(@PathVariable("document") document: String): AccountResponse {
        val account = getAccountUseCase.execute(GetAccountCommand.Builder(document).toCommand())

        return AccountResponse(
            document = account.document,
            balance = account.balance
        )
    }

    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Deposit made successfully"),
        ApiResponse(code = 404, message = "Account not found"),
        ApiResponse(code = 500, message = "Internal Server Error"),
    ])
    @PutMapping("/{document}/deposit/{amount}", produces=["application/json"])
    @ResponseStatus(HttpStatus.OK)
    fun deposit(@PathVariable("document") document: String, @PathVariable("amount") amount: Double){
        updateBalanceUseCase.execute(
            UpdateBalanceCommand.Builder(
            document = document,
            operation = Operation.DEPOSIT,
            amount = amount
        ).toCommand())
    }

    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Withdrawal successful"),
        ApiResponse(code = 404, message = "Account not found"),
        ApiResponse(code = 500, message = "Internal Server Error"),
    ])
    @PutMapping("/{document}/withdraw/{amount}", produces=["application/json"])
    @ResponseStatus(HttpStatus.OK)
    fun withdraw(@PathVariable("document") document: String, @PathVariable("amount") amount: Double){
        updateBalanceUseCase.execute(UpdateBalanceCommand.Builder(
            document = document,
            operation = Operation.WITHDRAW,
            amount = amount
        ).toCommand())
    }

    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Transfer successfully completed"),
        ApiResponse(code = 404, message = "Account not found"),
        ApiResponse(code = 500, message = "Internal Server Error"),
    ])
    @PutMapping("/{documentFrom}/transfer/{documentTo}/{amount}", produces=["application/json"])
    @ResponseStatus(HttpStatus.OK)
    fun transfer(@PathVariable("documentFrom") documentFrom: String,
                 @PathVariable("documentTo") documentTo: String,
                 @PathVariable("amount") amount: Double){
        transferAccountUseCase.execute(
            TransferAccountCommand.Builder(
            documentFrom = documentFrom,
            documentTo = documentTo,
            amount = amount
        ).toCommand())
    }

    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Return extract account"),
        ApiResponse(code = 404, message = "Account not found"),
        ApiResponse(code = 500, message = "Internal Server Error"),
    ])
    @GetMapping("/{document}/extract", produces=["application/json"])
    @ResponseStatus(HttpStatus.OK)
    fun extract(@PathVariable("document") document: String) =
        extractUseCase.execute(ExtractCommand.Builder(document).toCommand()).map {
            it.documentParent?.let { documentParent ->
                if(it.operation == Operation.DEPOSIT){
                    TransferDepositResponse(
                        id = it.id,
                        operation = "TRANSFER",
                        documentFrom = documentParent,
                        amount = it.amount,
                        date = it.date
                    )
                }else{
                    TransferWithdrawResponse(
                        id = it.id,
                        operation = "TRANSFER",
                        documentTo = documentParent,
                        amount = it.amount,
                        date = it.date
                    )
                }
            } ?: TransactionResponseImp(
                id = it.id,
                operation = it.operation.name,
                amount = it.amount,
                date = it.date
            )
        }
}