package com.example.cryptoscalpingapp.domain.usecase.wallet

import com.example.cryptoscalpingapp.data.database.local.WalletItem

class EditWalletItemUseCase(private  val walletListRepository: WalletListRepository) {
    suspend fun editWalletItem(walletItem: WalletItem) {
        walletListRepository.editWalletItem(walletItem)
    }
}