package com.example.cryptoscalpingapp.domain.usecase.wallet

import androidx.lifecycle.LiveData
import com.example.cryptoscalpingapp.data.database.local.WalletItem

interface WalletListRepository {
    fun getWalletList(): LiveData<List<WalletItem>>
    suspend fun addWalletItem(walletItem: WalletItem)
    suspend fun removeWalletItem(walletItem: WalletItem)
    suspend fun editWalletItem(walletItem: WalletItem)
    suspend fun getWalletItem(walletItemId: Int): WalletItem
}