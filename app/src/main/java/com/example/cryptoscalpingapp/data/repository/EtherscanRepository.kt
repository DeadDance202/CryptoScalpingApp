package com.example.cryptoscalpingapp.data.repository

import com.example.cryptoscalpingapp.data.BaseNetworkRepository
import com.example.cryptoscalpingapp.domain.model.TransactionItem
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient

class EtherscanRepository(client: OkHttpClient) : BaseNetworkRepository<List<TransactionItem>>(client) {
    override fun parseResponse(response: String): List<TransactionItem> {
        val jsonObject = Gson().fromJson(response, JsonObject::class.java)
        val resultArray = jsonObject.getAsJsonArray("result")
        return Gson().fromJson(resultArray, object : TypeToken<List<TransactionItem>>() {}.type)
    }
}