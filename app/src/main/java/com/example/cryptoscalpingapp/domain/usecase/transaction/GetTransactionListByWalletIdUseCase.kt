package com.example.cryptoscalpingapp.domain.usecase.transaction

import androidx.lifecycle.LiveData
import com.example.cryptoscalpingapp.data.database.local.TransactionItem

class GetTransactionListByWalletIdUseCase(private val transactionListRepository: TransactionListRepository) {

    fun getTransactionListByWalletIdDesc(walletId: Int): LiveData<List<TransactionItem>> {
        return transactionListRepository.getTransactionListByWalletIdDesc(walletId)
    }
}


