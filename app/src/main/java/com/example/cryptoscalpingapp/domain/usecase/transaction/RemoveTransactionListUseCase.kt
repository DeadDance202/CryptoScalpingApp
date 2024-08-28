package com.example.cryptoscalpingapp.domain.usecase.transaction

class RemoveTransactionListUseCase(private val transactionListRepository: TransactionListRepository) {

    fun removeTransactionList() {
        transactionListRepository.removeTransactionList()
    }

    fun removeTransactionListByWalletId(walletItemId: Int) {
        transactionListRepository.removeTransactionListByWalletId(walletItemId)
    }
}