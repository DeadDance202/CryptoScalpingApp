package com.example.cryptoscalpingapp.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.cryptoscalpingapp.data.database.local.TransactionItem
import com.google.gson.Gson
import java.io.File
import java.io.IOException

class TransactionItemViewModel(private val application: Application) :
    AndroidViewModel(application) {

//    private val repository = TransactionListRepositoryImpl

    //    private val getTransactionItemUseCase = GetTransactionItemUseCase(repository)
//    private val clearTransactionListUseCase = ClearTransactionListUseCase(repository)
//    private val getTransactionListUseCase = GetTransactionListUseCase(repository)


    private val _transactionList = MutableLiveData<List<TransactionItem>>()
    val transactionList
        get() = _transactionList


    fun getTransactionListFromFile(fileName: String): List<TransactionItem> {
        val transactions = mutableListOf<TransactionItem>()
        try {
            // Получение директории файлов приложения
            val file = File(application.getExternalFilesDir(null), fileName)
            // Проверка, существует ли файл
            if (file.exists()) {
                // Чтение содержимого файла
                val json = file.readText()
                // Преобразование JSON обратно в список объектов
                Gson().fromJson(json, Array<TransactionItem>::class.java).toList()
            } else {
                // Возврат null, если файл не существует
                null
            }
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }

        _transactionList.value = transactions
        return transactions
    }

    private fun parseTransactionItem(line: String): TransactionItem {
        val parts = line.split(",") // Используйте соответствующий разделитель, если не запятая

        if (parts.size != 19) {
            throw IllegalArgumentException("Invalid input line, expected 19 fields but got ${parts.size}")
        }

        return TransactionItem(
            blockNumber = parts[0],
            timeStamp = parts[1],
            hash = parts[2],
            nonce = parts[3],
            blockHash = parts[4],
            from = parts[5],
            contractAddress = parts[6],
            to = parts[7],
            value = parts[8],
            tokenName = parts[9],
            tokenSymbol = parts[10],
            tokenDecimal = parts[11],
            transactionIndex = parts[12],
            gas = parts[13],
            gasPrice = parts[14],
            gasUsed = parts[15],
            cumulativeGasUsed = parts[16],
            input = parts[17],
            confirmations = parts[18]
        )
    }

}