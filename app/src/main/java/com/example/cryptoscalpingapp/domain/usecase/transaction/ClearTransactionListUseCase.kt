package com.example.cryptoscalpingapp.domain.usecase.transaction

class ClearTransactionListUseCase(private val transactionListRepository: TransactionListRepository) {

    fun clearTransactionList() {
        transactionListRepository.clearTransactionList()
    }
}