package br.com.app.bank.manager.app.configuration

import br.com.app.bank.manager.core.repository.AccountRepository
import br.com.app.bank.manager.core.repository.TransactionRepository
import br.com.app.bank.manager.core.usecase.CreateAccountUseCase
import br.com.app.bank.manager.core.usecase.ExtractUseCase
import br.com.app.bank.manager.core.usecase.GetAccountUseCase
import br.com.app.bank.manager.core.usecase.TransferAccountUseCase
import br.com.app.bank.manager.core.usecase.UpdateBalanceUseCase
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ConfigurationApplication {

    @Bean
    fun createAccountUseCase(accountRepository: AccountRepository) =
        CreateAccountUseCase(accountRepository)

    @Bean
    fun getAccountUseCase(accountRepository: AccountRepository) =
        GetAccountUseCase(accountRepository)

    @Bean
    fun updateBalanceUseCase(accountRepository: AccountRepository) =
        UpdateBalanceUseCase(accountRepository)

    @Bean
    fun transferAccountUseCase(accountRepository: AccountRepository) =
        TransferAccountUseCase(accountRepository)

    @Bean
    fun extractUseCase(transactionRepository: TransactionRepository, accountRepository: AccountRepository) =
        ExtractUseCase(transactionRepository, accountRepository)
}