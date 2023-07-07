package br.com.app.bank.manager.app.configuration

import br.com.app.bank.manager.core.adapters.AccountPersistenceAdapter
import br.com.app.bank.manager.core.adapters.TransactionPersistenceAdapter
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
    fun createAccountUseCase(accountPersistenceAdapter: AccountPersistenceAdapter) =
        CreateAccountUseCase(accountPersistenceAdapter)

    @Bean
    fun getAccountUseCase(accountPersistenceAdapter: AccountPersistenceAdapter) =
        GetAccountUseCase(accountPersistenceAdapter)

    @Bean
    fun updateBalanceUseCase(accountPersistenceAdapter: AccountPersistenceAdapter) =
        UpdateBalanceUseCase(accountPersistenceAdapter)

    @Bean
    fun transferAccountUseCase(accountPersistenceAdapter: AccountPersistenceAdapter) =
        TransferAccountUseCase(accountPersistenceAdapter)

    @Bean
    fun extractUseCase(transactionPersistenceAdapter: TransactionPersistenceAdapter, accountPersistenceAdapter: AccountPersistenceAdapter) =
        ExtractUseCase(transactionPersistenceAdapter, accountPersistenceAdapter)
}