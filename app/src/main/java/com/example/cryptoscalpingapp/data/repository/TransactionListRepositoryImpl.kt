package com.example.cryptoscalpingapp.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.cryptoscalpingapp.data.database.local.AppDatabase
import com.example.cryptoscalpingapp.data.database.local.TransactionItem
import com.example.cryptoscalpingapp.domain.usecase.transaction.TransactionListRepository

class TransactionListRepositoryImpl(context: Context) : TransactionListRepository {
    private var transactionDao = AppDatabase.getDatabase(context).transactionDao()

    override fun getTransactionList(): LiveData<List<TransactionItem>> {
        return transactionDao.getAllTransactionsDesc()
    }

    override fun getTransactionListByWalletIdDesc(walletItemId: Int): LiveData<List<TransactionItem>> {
        return transactionDao.getAllTransactionsByWalletIdDesc(walletItemId)
    }

    override fun removeTransactionListByWalletId(walletItemId: Int) {
        transactionDao.deleteAllTransactionsByWalletId(walletItemId)
    }

    override fun removeTransactionList() {
        TODO("Not yet implemented")
    }

    override suspend fun addTransactionItem(transactionItem: TransactionItem) {
        transactionDao.insertTransactionItem(transactionItem)
    }
}