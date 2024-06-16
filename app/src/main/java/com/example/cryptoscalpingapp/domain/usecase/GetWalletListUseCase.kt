package com.example.cryptoscalpingapp.domain.usecase

import androidx.lifecycle.LiveData
import com.example.cryptoscalpingapp.domain.model.WalletItem

class GetWalletListUseCase(private  val walletListRepository: WalletListRepository) {

    fun getWalletList(): LiveData<List<WalletItem>> {
        return  walletListRepository.getWalletList()
    }
}