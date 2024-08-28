package com.example.cryptoscalpingapp.domain.usecase.transaction

import androidx.lifecycle.LiveData
import com.example.cryptoscalpingapp.data.database.local.TransactionItem

interface TransactionListRepository {
    fun getTransactionList(): LiveData<List<TransactionItem>>
    fun removeTransactionListByWalletId(walletItemId : Int)
    fun removeTransactionList()
    suspend fun addTransactionItem(transactionItem: TransactionItem)
    fun getTransactionListByWalletId(walletItemId: Int): List<TransactionItem>
}