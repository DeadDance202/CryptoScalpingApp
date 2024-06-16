package com.example.cryptoscalpingapp.presentation.viewmodel

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cryptoscalpingapp.data.repository.WalletListRepositoryImpl
import com.example.cryptoscalpingapp.data.repository.WalletListRepositoryImpl.getWalletList
import com.example.cryptoscalpingapp.domain.model.WalletItem
import com.example.cryptoscalpingapp.domain.usecase.EditWalletItemUseCase
import com.example.cryptoscalpingapp.domain.usecase.GetWalletListUseCase
import com.example.cryptoscalpingapp.domain.usecase.RemoveWalletItemUseCase
import okhttp3.internal.notify

class WalletListViewModel : ViewModel() {
    private val repository = WalletListRepositoryImpl

    private val getWalletListUseCase = GetWalletListUseCase(repository)
    private val removeWalletItemUseCase = RemoveWalletItemUseCase(repository)
    private val editWalletItemUseCase = EditWalletItemUseCase(repository)

    val walletList = getWalletListUseCase.getWalletList()
    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String>
        get() = _toastMessage

    fun removeWalletItem(walletItem: WalletItem) {
        removeWalletItemUseCase.removeWalletItem(walletItem)
        getWalletList()
    }

    fun changedEnableStateWalletItem(walletItem: WalletItem) {
        val newItem = walletItem.copy(enabled = !walletItem.enabled)
        editWalletItemUseCase.editWalletItem(newItem)
        getWalletList()
    }
}