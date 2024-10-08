package com.example.cryptoscalpingapp.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.cryptoscalpingapp.data.database.local.AppDatabase
import com.example.cryptoscalpingapp.data.database.local.WalletItem
import com.example.cryptoscalpingapp.domain.usecase.wallet.WalletListRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WalletListRepositoryImpl(context: Context) : WalletListRepository {
    private var walletDao = AppDatabase.getDatabase(context).walletDao()

    init {
        getWalletList()
    }

    override suspend fun addWalletItem(walletItem: WalletItem) {
        CoroutineScope(Dispatchers.IO).launch {
            walletDao.insertWalletItem(walletItem)
        }
    }

    override suspend fun removeWalletItem(walletItem: WalletItem) {
        CoroutineScope(Dispatchers.IO).launch {
            walletDao.deleteWalletItem(walletItem)
        }
    }

    override suspend fun editWalletItem(walletItem: WalletItem) {
        CoroutineScope(Dispatchers.IO).launch {
            walletDao.updateWalletItem(walletItem)
        }
    }

    override suspend fun getWalletItem(walletItemId: Int): WalletItem {
        return withContext(Dispatchers.IO) {
             walletDao.getWalletItem(walletItemId)
        }
    }

    override fun getWalletList(): LiveData<List<WalletItem>> {
        return walletDao.getAllWalletItems()
    }
}