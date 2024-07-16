package com.example.cryptoscalpingapp.domain.usecase.transaction

import androidx.lifecycle.LiveData
import com.example.cryptoscalpingapp.data.database.local.TransactionItem

interface TransactionListRepository {
    fun getTransactionList(fileName: String): LiveData<List<TransactionItem>>
    fun clearTransactionList()
}