package com.example.cryptoscalpingapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoscalpingapp.data.database.local.WalletItem
import com.example.cryptoscalpingapp.domain.usecase.wallet.AddWalletItemUseCase
import com.example.cryptoscalpingapp.domain.usecase.wallet.EditWalletItemUseCase
import com.example.cryptoscalpingapp.domain.usecase.wallet.GetWalletItemUseCase
import com.example.cryptoscalpingapp.domain.usecase.wallet.WalletListRepository
import com.example.cryptoscalpingapp.presentation.utils.StringUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WalletItemViewModel @Inject constructor(
    private val repository: WalletListRepository
) : ViewModel() {
    private val getWalletItemUseCase = GetWalletItemUseCase(repository)
    private val addWalletItemUseCase = AddWalletItemUseCase(repository)
    private val editWalletItemUseCase = EditWalletItemUseCase(repository)

    private val _walletItem = MutableLiveData<WalletItem>()
    val walletItem
        get() = _walletItem

    private val _errorInputAddress = MutableLiveData<Boolean>()
    val errorInputAddress: LiveData<Boolean>
        get() = _errorInputAddress

    private val _invalidInputName = MutableLiveData<Boolean>()
    val invalidInputName: LiveData<Boolean>
        get() = _invalidInputName

    suspend fun getWalletItem(walletItemId: Int) {
        _walletItem.value = getWalletItemUseCase.getWalletItem(walletItemId)
    }

    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen

    fun addWalletItem(inputName: String?, inputAddress: String?) {
        val address = parseAddress(inputAddress)
        val name = parseName(inputName)
        if (validateInputs(address, name)) {
            val shortAddress = transformAddress(address)
            val walletItem = WalletItem(
                name = name,
                address = address,
                shortAddress = shortAddress,
                enabled = false
            )
            viewModelScope.launch {
                addWalletItemUseCase.addWalletItem(walletItem)
                finnishWork()
            }
        }
    }

    suspend fun editWalletItem(inputName: String?, inputAddress: String?) {
        val address = parseAddress(inputAddress)
        val name = parseName(inputName)
        if (validateInputs(address, name)) {
            val shortAddress = transformAddress(address)
            _walletItem.value?.let {
                val walletItem =
                    it.copy(
                        name = name,
                        shortAddress = shortAddress,
                        address = address
                    )
                viewModelScope.launch {
                    editWalletItemUseCase.editWalletItem(walletItem)
                }
                finnishWork()
            }
        }

    }

    private fun transformAddress(address: String): String {

        return StringUtils.transformString(address)
    }

    private fun parseName(inputName: String?): String {
        return parseString(inputName)
    }

    private fun parseAddress(inputAddress: String?): String {
        return parseString(inputAddress)
    }

    private fun parseString(inputString: String?): String {
        return inputString?.trim() ?: ""
    }

    private fun validateInputs(inputAddress: String, inputName: String): Boolean {
        var result = true
        if (inputName.length >= 20) {
            _invalidInputName.value = true
            result = false
        }

        if (inputAddress.isBlank()) {
            _errorInputAddress.value = true
            result = false
        }

        return result
    }

    fun resetInvalidInputName() {
        _invalidInputName.value = false
    }

    fun resetErrorInputAddress() {
        _errorInputAddress.value = false
    }

    private fun finnishWork() {
        _shouldCloseScreen.value = Unit
    }

}