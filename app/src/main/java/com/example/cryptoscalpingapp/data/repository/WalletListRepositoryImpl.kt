package com.example.cryptoscalpingapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cryptoscalpingapp.domain.model.WalletItem
import com.example.cryptoscalpingapp.domain.usecase.WalletListRepository

object WalletListRepositoryImpl : WalletListRepository {

    private val walletList = sortedSetOf<WalletItem>({ o1, o2 -> o1.id.compareTo(o2.id) })
    private val walletListLD = MutableLiveData<List<WalletItem>>()
    private var autoIncrementId = 0

    init {
        for (i in 0 until 10) {
            val item = WalletItem("Name  $i", "string", "ss..ss", false)
            addWalletItem(item)
        }
    }

    override fun addWalletItem(walletItem: WalletItem) {
        if (walletItem.id == WalletItem.UNDEFINED_ID) {
            walletItem.id = autoIncrementId++
        }
        walletList.add(walletItem)
        updateList()
    }

    override fun removeWalletItem(walletItem: WalletItem) {
        walletList.remove(walletItem)
        updateList()
    }

    override fun editWalletItem(walletItem: WalletItem) {
        val oldElement = getWalletItem(walletItem.id)
        walletList.remove(oldElement)
        addWalletItem(walletItem)
    }

    override fun getWalletItem(walletItemId: Int): WalletItem {
        return walletList.find {
            it.id == walletItemId
        } ?: throw RuntimeException("Element with id $walletItemId not found")
    }

    override fun getWalletList(): LiveData<List<WalletItem>> {
        return walletListLD
    }

    private fun updateList() {
        walletListLD.value = walletList.toList()
    }
}