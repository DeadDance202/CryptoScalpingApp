package com.example.cryptoscalpingapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.cryptoscalpingapp.data.database.local.WalletItem
import com.example.cryptoscalpingapp.domain.usecase.wallet.EditWalletItemUseCase
import com.example.cryptoscalpingapp.domain.usecase.wallet.GetWalletListUseCase
import com.example.cryptoscalpingapp.domain.usecase.wallet.RemoveWalletItemUseCase
import com.example.cryptoscalpingapp.domain.usecase.wallet.WalletListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WalletListViewModel @Inject constructor(
    private val repository: WalletListRepository
) : ViewModel() {

    private val getWalletListUseCase = GetWalletListUseCase(repository)
    private val removeWalletItemUseCase = RemoveWalletItemUseCase(repository)
    private val editWalletItemUseCase = EditWalletItemUseCase(repository)
    val walletList: LiveData<List<WalletItem>> = getWalletListUseCase.getWalletList()

    fun removeWalletItem(walletItem: WalletItem) {
        CoroutineScope(Dispatchers.IO).launch {
            removeWalletItemUseCase.removeWalletItem(walletItem)
        }
        getWalletListUseCase.getWalletList()
    }

    suspend fun changedEnableStateWalletItem(walletItem: WalletItem): WalletItem {
//        Log.d("walletItem_change", walletItem.toString())
        val newItem = walletItem.copy(enabled = !walletItem.enabled)
        editWalletItemUseCase.editWalletItem(newItem)
        getWalletListUseCase.getWalletList()
        return newItem
    }
}