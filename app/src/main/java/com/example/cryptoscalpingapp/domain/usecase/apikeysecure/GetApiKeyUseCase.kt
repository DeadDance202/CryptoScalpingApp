package com.example.cryptoscalpingapp.domain.usecase.apikeysecure

class GetApiKeyUseCase(private val aPIKeySecureRepository: APIKeySecureRepository) {
    fun getApiKeyUseCase(): String? {
        return aPIKeySecureRepository.getApiKeyUseCase()
    }
}