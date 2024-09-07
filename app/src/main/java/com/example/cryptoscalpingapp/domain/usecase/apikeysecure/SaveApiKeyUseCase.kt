package com.example.cryptoscalpingapp.domain.usecase.apikeysecure

class SaveApiKeyUseCase(private val aPIKeySecureRepository: APIKeySecureRepository) {
    fun saveApiKeyUseCase(apiKey: String) {
        aPIKeySecureRepository.saveApiKeyUseCase(apiKey)
    }
}