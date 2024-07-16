package com.example.cryptoscalpingapp.domain.usecase.apitransaction

import com.example.cryptoscalpingapp.data.repository.EtherscanRepository
import com.example.cryptoscalpingapp.data.database.local.TransactionItem

class FetchTransactionListUseCase(private val repository: EtherscanRepository) {
    suspend fun fetchTransactionList(url: String): List<TransactionItem> {
        return repository.fetchData(url)
    }
}