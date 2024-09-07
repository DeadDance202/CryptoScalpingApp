package com.example.cryptoscalpingapp.domain.usecase.apikeysecure

class ClearApiKeyUseCase(private val aPIKeySecureRepository: APIKeySecureRepository) {
    fun clearApiKeyUseCase() {
        aPIKeySecureRepository.clearApiKeyUseCase()
    }
}