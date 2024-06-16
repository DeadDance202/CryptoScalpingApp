package com.example.cryptoscalpingapp.domain.usecase

import com.example.cryptoscalpingapp.domain.model.WalletItem

class GetWalletItemUseCase(private val walletListRepository: WalletListRepository) {

    fun getWalletItem(walletItemId: Int): WalletItem {
        return  walletListRepository.getWalletItem(walletItemId)
    }
}