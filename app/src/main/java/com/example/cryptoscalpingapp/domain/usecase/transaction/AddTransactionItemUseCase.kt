package com.example.cryptoscalpingapp.domain.usecase.transaction

import com.example.cryptoscalpingapp.data.database.local.TransactionItem

class AddTransactionItemUseCase(private val transactionListRepository: TransactionListRepository) {

    suspend fun addTransactionItem(transactionItem: TransactionItem) {
        transactionListRepository.addTransactionItem(transactionItem)
    }
}