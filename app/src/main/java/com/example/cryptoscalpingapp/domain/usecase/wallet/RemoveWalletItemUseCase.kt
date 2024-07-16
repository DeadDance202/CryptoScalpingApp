package com.example.cryptoscalpingapp.domain.usecase.wallet

import com.example.cryptoscalpingapp.data.database.local.WalletItem

class RemoveWalletItemUseCase(private val walletListRepository: WalletListRepository) {

    suspend fun removeWalletItem(walletItem: WalletItem) {
        walletListRepository.removeWalletItem(walletItem)
    }
}