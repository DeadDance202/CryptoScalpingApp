package com.example.cryptoscalpingapp.domain.usecase

import com.example.cryptoscalpingapp.data.repository.EtherscanRepository
import com.example.cryptoscalpingapp.domain.model.TransactionItem

class GetTransactionListUseCase(private val repository: EtherscanRepository) {
    suspend fun execute(url: String): List<TransactionItem> {
        return repository.fetchData(url)
    }
}