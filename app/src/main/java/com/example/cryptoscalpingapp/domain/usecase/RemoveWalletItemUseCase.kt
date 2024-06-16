package com.example.cryptoscalpingapp.domain.usecase

import com.example.cryptoscalpingapp.domain.model.WalletItem

class RemoveWalletItemUseCase(private val walletListRepository: WalletListRepository) {

    fun removeWalletItem(walletItem: WalletItem) {
        walletListRepository.removeWalletItem(walletItem)
    }
}