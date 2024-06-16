package com.example.cryptoscalpingapp.domain.usecase

import com.example.cryptoscalpingapp.domain.model.WalletItem

class EditWalletItemUseCase(private  val walletListRepository: WalletListRepository) {

    fun editWalletItem(walletItem: WalletItem) {
        walletListRepository.editWalletItem(walletItem)
    }
}