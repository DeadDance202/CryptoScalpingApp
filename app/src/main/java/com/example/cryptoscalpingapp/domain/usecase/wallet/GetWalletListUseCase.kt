package com.example.cryptoscalpingapp.domain.usecase.wallet

import androidx.lifecycle.LiveData
import com.example.cryptoscalpingapp.data.database.local.WalletItem

class GetWalletListUseCase(private val walletListRepository: WalletListRepository) {
    fun getWalletList(): LiveData<List<WalletItem>> {
        return walletListRepository.getWalletList()
    }
}