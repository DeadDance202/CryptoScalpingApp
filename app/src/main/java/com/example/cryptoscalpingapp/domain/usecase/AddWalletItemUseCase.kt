package com.example.cryptoscalpingapp.domain.usecase

import com.example.cryptoscalpingapp.domain.model.WalletItem

class AddWalletItemUseCase(private  val walletListRepository: WalletListRepository) {

    fun addWalletItem(walletItem: WalletItem) {
        walletListRepository.addWalletItem(walletItem)
    }
}