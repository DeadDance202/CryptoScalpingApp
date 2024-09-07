package com.example.cryptoscalpingapp.data.repository

import android.content.Context
import com.example.cryptoscalpingapp.data.datasource.local.ApiKeySecureStorage
import com.example.cryptoscalpingapp.domain.usecase.apikeysecure.APIKeySecureRepository

class ApiKeySecureRepositoryImp(context: Context) : APIKeySecureRepository {

   val apiKeySecureStorage = ApiKeySecureStorage(context)
    override fun saveApiKeyUseCase(apiKey: String) {
        apiKeySecureStorage.saveApiKey(apiKey)
    }

    override fun getApiKeyUseCase(): String? {
        return apiKeySecureStorage.getApiKey()
    }

    override fun clearApiKeyUseCase() {
        apiKeySecureStorage.clearApiKey()
    }
}