package com.example.cryptoscalpingapp.domain.usecase.wallet

import com.example.cryptoscalpingapp.data.database.local.WalletItem

class AddWalletItemUseCase(private val walletListRepository: WalletListRepository) {
    suspend fun addWalletItem(walletItem: WalletItem) {
        walletListRepository.addWalletItem(walletItem)
    }
}