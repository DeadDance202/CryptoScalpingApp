package com.example.cryptoscalpingapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoscalpingapp.domain.usecase.apikeysecure.APIKeySecureRepository
import com.example.cryptoscalpingapp.domain.usecase.apikeysecure.ClearApiKeyUseCase
import com.example.cryptoscalpingapp.domain.usecase.apikeysecure.GetApiKeyUseCase
import com.example.cryptoscalpingapp.domain.usecase.apikeysecure.SaveApiKeyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class APIKeySecureViewModel @Inject constructor(
    private val repository: APIKeySecureRepository
) : ViewModel() {
    private val saveApiKeyUseCase = SaveApiKeyUseCase(repository)
    private val getApiKeyUseCase = GetApiKeyUseCase(repository)
    private val clearApiKeyUseCase = ClearApiKeyUseCase(repository)

    fun saveApiKey(apiKey: String) {
        viewModelScope.launch {
            saveApiKeyUseCase.saveApiKeyUseCase(apiKey)
        }
    }

    fun getApiKey(): String? {
        return getApiKeyUseCase.getApiKeyUseCase()
    }

    fun clearApiKey() {
        viewModelScope.launch {
            clearApiKeyUseCase.clearApiKeyUseCase()
        }
    }
}