package br.com.app.bank.manager.app.adapters.jpa

import br.com.app.bank.manager.app.adapters.jpa.entity.AccountEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountJPARepository: CrudRepository<AccountEntity, String>