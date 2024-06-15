package com.example.cryptoscalpingapp.presentation.viewmodel

import androidx.lifecycle.*
import com.example.cryptoscalpingapp.domain.usecase.GetTransactionItemListUseCase
import com.example.cryptoscalpingapp.domain.model.TransactionItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TransactionItemsViewModel(private val getTransactionsUseCase: GetTransactionItemListUseCase) :
    ViewModel() {

    private val _transactions = MutableLiveData<List<TransactionItem>>()
    val transactions: LiveData<List<TransactionItem>>
        get() = _transactions

    val etherscanUrl: String = "https://api.etherscan.io/api"
    val address: String = "0xf70da97812CB96acDF810712Aa562db8dfA3dbEF"
    val page: String = "1"
    val offset: String = "2"
    val sort: String = "asc"
    val apiKey: String = "PSJW6F99HTNACN1ENWVCTZZ2P1U2I9AU9Q"
    val startBlock: String = "0"
    val endBlock: String = "999999999"
    var url = "$etherscanUrl?module=account" +
            "&action=tokentx" +
            "&address=$address" +
            "&page=$page" +
            "&offset=$offset" +
            "&startblock=$startBlock" +
            "&endblock=$endBlock" +
            "&sort=$sort" +
            "&apikey=$apiKey"

    init {
        startFetchingTransactionsPeriodically()
    }

    private fun startFetchingTransactionsPeriodically() {
        viewModelScope.launch {
            while (true) {
                fetchTransactions()
                delay(30000) // 5 минут в миллисекундах
            }
        }
    }

    private suspend fun fetchTransactions() {
        try {
            val result = getTransactionsUseCase.execute(url)
            _transactions.value = result
        } catch (e: Exception) {
            _transactions.value = emptyList() // Обработка ошибки
        }
    }

    class TransactionItemsViewModelFactory(private val getTransactionsUseCase: GetTransactionItemListUseCase) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TransactionItemsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return TransactionItemsViewModel(getTransactionsUseCase) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
