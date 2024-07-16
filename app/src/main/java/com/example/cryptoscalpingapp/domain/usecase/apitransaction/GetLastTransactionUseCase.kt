package com.example.cryptoscalpingapp.domain.usecase.apitransaction

import com.example.cryptoscalpingapp.data.repository.EtherscanRepository
import com.example.cryptoscalpingapp.data.database.local.TransactionItem

class GetLastTransactionUseCase(private val repository: EtherscanRepository) {
    suspend fun getLastTransaction(): TransactionItem? {
        TODO()
//        return repository.getLastTransaction()
    }
}