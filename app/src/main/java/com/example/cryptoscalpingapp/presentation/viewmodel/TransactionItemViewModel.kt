package com.example.cryptoscalpingapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.cryptoscalpingapp.data.database.local.TransactionItem
import com.example.cryptoscalpingapp.domain.usecase.transaction.GetTransactionListByWalletIdUseCase
import com.example.cryptoscalpingapp.domain.usecase.transaction.TransactionListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TransactionItemViewModel @Inject constructor(
    private val repository: TransactionListRepository
) : ViewModel() {

    private val getTransactionListUseCase = GetTransactionListByWalletIdUseCase(repository)

    private val _transactionList = MediatorLiveData<List<TransactionItem>>()
    val transactionList: LiveData<List<TransactionItem>> = _transactionList

    fun getTransactionListByWalletId(walletId: Int) {
        val liveData = getTransactionListUseCase.getTransactionListByWalletIdDesc(walletId)
        _transactionList.addSource(liveData) { transactions ->
            _transactionList.value = transactions
        }
    }
}