package com.example.cryptoscalpingapp.domain.usecase

import com.example.cryptoscalpingapp.data.repository.EtherscanRepository
import com.example.cryptoscalpingapp.domain.model.TransactionItem

class RemoveTransactionItemUseCase(private val repository: EtherscanRepository) {

    fun removeTransactionItem(item: TransactionItem) {
        repository.removeItem(item)
    }
}