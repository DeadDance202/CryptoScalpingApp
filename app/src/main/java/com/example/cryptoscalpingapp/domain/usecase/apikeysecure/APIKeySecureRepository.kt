package com.example.cryptoscalpingapp.domain.usecase.apikeysecure

interface APIKeySecureRepository {
    fun saveApiKeyUseCase(apiKey: String)
    fun getApiKeyUseCase(): String?
    fun clearApiKeyUseCase()
}