package com.example.cryptoscalpingapp.domain.usecase.wallet

import com.example.cryptoscalpingapp.data.database.local.WalletItem

class GetWalletItemUseCase(private val walletListRepository: WalletListRepository) {
    suspend fun getWalletItem(walletItemId: Int): WalletItem {
        return  walletListRepository.getWalletItem(walletItemId)
    }
}