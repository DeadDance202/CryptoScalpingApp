package com.example.cryptoscalpingapp.domain.usecase

import androidx.lifecycle.LiveData
import com.example.cryptoscalpingapp.domain.model.WalletItem

interface WalletListRepository {

    fun addWalletItem(walletItem: WalletItem)
    fun removeWalletItem(walletItem: WalletItem)
    fun editWalletItem(walletItem: WalletItem)
    fun getWalletList(): LiveData<List<WalletItem>>
    fun getWalletItem(walletItemId: Int): WalletItem

}