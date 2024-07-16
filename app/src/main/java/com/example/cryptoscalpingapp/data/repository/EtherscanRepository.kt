package com.example.cryptoscalpingapp.data.repository

import com.example.cryptoscalpingapp.data.BaseNetworkRepository
import com.example.cryptoscalpingapp.data.database.local.TransactionItem
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient

class EtherscanRepository(client: OkHttpClient) :
    BaseNetworkRepository<List<TransactionItem>>(client)
{
//    private val transactionList = mutableListOf<TransactionItem>()

    override fun parseResponse(response: String): List<TransactionItem> {
        val jsonObject = Gson().fromJson(response, JsonObject::class.java)
        val resultArray = jsonObject.getAsJsonArray("result")
        return Gson().fromJson(resultArray, object : TypeToken<List<TransactionItem>>() {}.type)
    }

//    override suspend fun saveTransactions(transactions: List<TransactionItem>) {
//        transactionList.addAll(transactions)
//    }
//
//    override suspend fun getLastTransaction(): TransactionItem? {
//        return transactionList.lastOrNull()
//    }

//    override suspend fun fetchTransactions(url: String): List<TransactionItem> {
//        TODO()
//        return get(url) // Assuming `get` method is defined in BaseNetworkRepository
//    }
}