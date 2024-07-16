package com.example.cryptoscalpingapp.domain.usecase.apitransaction

import com.example.cryptoscalpingapp.data.database.local.TransactionItem

interface APITransactionRepository {
    suspend fun getLastTransaction(): TransactionItem?
    suspend fun fetchTransactions(url: String): List<TransactionItem>
}