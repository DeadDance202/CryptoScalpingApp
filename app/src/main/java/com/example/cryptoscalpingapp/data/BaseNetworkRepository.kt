package com.example.cryptoscalpingapp.data

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

abstract class BaseNetworkRepository<T>(private val client: OkHttpClient) {
    suspend fun fetchData(url: String): T {
        return withContext(Dispatchers.IO) {
            val request = Request.Builder()
                .url(url)
                .build()

            val response = client.newCall(request).execute()
            Log.d("responce", response.toString())
            if (response.isSuccessful) {
                response.body?.let {
                    parseResponse(it.string())
                } ?: throw IOException("Empty response body")
            } else {
                throw IOException("Unexpected code $response")
            }
        }
    }

    protected abstract fun parseResponse(response: String): T
}