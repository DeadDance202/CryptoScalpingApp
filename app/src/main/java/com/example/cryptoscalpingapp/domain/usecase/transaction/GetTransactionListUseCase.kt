package com.example.cryptoscalpingapp.domain.usecase.transaction

import androidx.lifecycle.LiveData
import com.example.cryptoscalpingapp.data.database.local.TransactionItem

class GetTransactionListUseCase(private val transactionListRepository: TransactionListRepository) {
    fun getTransactionList(): LiveData<List<TransactionItem>> {
        return transactionListRepository.getTransactionList()
    }

    fun getTransactionListByWalletId(walletId: Int): List<TransactionItem> {
        return transactionListRepository.getTransactionListByWalletId(walletId)
    }
}