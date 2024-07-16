package com.example.cryptoscalpingapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cryptoscalpingapp.data.database.local.TransactionItem
import com.example.cryptoscalpingapp.domain.usecase.transaction.TransactionListRepository

object TransactionListRepositoryImpl : TransactionListRepository {
    private val transactionList = sortedSetOf<TransactionItem>({ o1, o2 -> o2.id.compareTo(o1.id) })
    private val transactionListLD = MutableLiveData<List<TransactionItem>>()
    private var autoIncrementId = 0

    override fun getTransactionList(fileName: String): LiveData<List<TransactionItem>> {
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
        return transactionListLD
    }

//    private fun addTransactionItem(transactionItem: TransactionItem) {
//        if (transactionItem.id == TransactionItem.UNDEFINED_ID) {
//            transactionItem.id = autoIncrementId++
//        }
//        transactionList.add(transactionItem)
//        updateList()
//    }

    override fun clearTransactionList() {
        transactionList.clear()
    }

    private fun updateList() {
        transactionListLD.value = transactionList.toList()
    }

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