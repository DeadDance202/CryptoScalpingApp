package com.example.cryptoscalpingapp.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.cryptoscalpingapp.data.database.local.AppDatabase
import com.example.cryptoscalpingapp.data.database.local.TransactionItem
import com.example.cryptoscalpingapp.domain.usecase.transaction.TransactionListRepository

class TransactionListRepositoryImpl(context: Context) : TransactionListRepository {
//    private val transactionList = sortedSetOf<TransactionItem>({ o1, o2 -> o2.id.compareTo(o1.id) })
//    private val transactionListLD = MutableLiveData<List<TransactionItem>>()
    private var transactionDao = AppDatabase.getDatabase(context).transactionDao()

    override fun getTransactionList(): LiveData<List<TransactionItem>> {
       return transactionDao.getAllTransactionsDesc()
    }

    override fun getTransactionListByWalletId(walletItemId: Int): List<TransactionItem> {
        return transactionDao.getAllTransactionsByWalletId(walletItemId)
    }

//    private fun addTransactionItem(transactionItem: TransactionItem) {
//        if (transactionItem.id == TransactionItem.UNDEFINED_ID) {
//            transactionItem.id = autoIncrementId++
//        }
//        transactionList.add(transactionItem)
//        updateList()
//    }

    override fun removeTransactionListByWalletId(walletItemId : Int) {
        transactionDao.deleteAllTransactionsByWalletId(walletItemId)
    }

    override fun removeTransactionList() {
        TODO("Not yet implemented")
    }

    override suspend fun addTransactionItem(transactionItem: TransactionItem) {
        transactionDao.insertTransactionItem(transactionItem)
    }

//    private fun updateList() {
//        transactionListLD.value = transactionList.toList()
//    }

//    override fun getTransactionItem(address: String, transactionId: String): TransactionItem? {
//        val fileName = "${address}.txt"
//        val file = File(application.filesDir, fileName)
//        if (file.exists()) {
//            file.forEachLine { line ->
//                val transactionItem = parseTransactionItem(line)
//                if (transactionItem.id == transactionId) {
//                    return transactionItem
//                }
//            }
//        }
//        return null
//    }


//    private fun parseTransactionItem(line: String): TransactionItem {
//        // Реализация парсинга строки в объект TransactionItem
//        // ...
//    }


}