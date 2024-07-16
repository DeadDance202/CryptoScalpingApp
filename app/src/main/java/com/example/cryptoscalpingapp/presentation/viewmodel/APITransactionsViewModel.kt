package com.example.cryptoscalpingapp.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoscalpingapp.data.database.local.TransactionItem
import com.example.cryptoscalpingapp.data.repository.EtherscanRepository
import com.example.cryptoscalpingapp.domain.entity.ERC20
import com.example.cryptoscalpingapp.domain.usecase.apitransaction.FetchTransactionListUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient

class APITransactionsViewModel(private val application: Application) : AndroidViewModel(application) {

    private var requestUrl = ""
    private val client = OkHttpClient()
    private val repository = EtherscanRepository(client)
//    private val getLastTransactionUseCase = GetLastTransactionUseCase(repository)
    private val fetchTransactionListUseCase = FetchTransactionListUseCase(repository)

    fun startFetchingTransactionsPeriodically(address: String) {
        requestUrl = ERC20(address = address).buildUrl()
        viewModelScope.launch {
            while (true) {
                fetchTransactions(requestUrl)
                delay(3000)
            }
        }
    }

//    suspend fun getLastTransaction(): TransactionItem? {
//        return getLastTransactionUseCase.getLastTransaction()
//    }

    private suspend fun fetchTransactions(url: String): List<TransactionItem> {
        return try {
            fetchTransactionListUseCase.fetchTransactionList(url)
//            val transactions = fetchTransactionListUseCase.fetchTransactionList(url)
//            val uniqueTransactions = transactions.distinctBy { it.hash } // Assuming `hash` is unique identifier
//            uniqueTransactions
            //TODO: need to get unique transaction item
        } catch (e: Exception) {
            emptyList() // Обработка ошибки
        }
    }

//    class APITransactionsViewModelFactory(private val fetchTransactionListUseCase: FetchTransactionListUseCase) :
//        ViewModelProvider.Factory {
//        override fun <T : ViewModel> create(modelClass: Class<T>): T {
//            if (modelClass.isAssignableFrom(APITransactionsViewModel::class.java)) {
//                @Suppress("UNCHECKED_CAST")
//                return APITransactionsViewModel(fetchTransactionListUseCase) as T
//            }
//            throw IllegalArgumentException("Unknown ViewModel class")
//        }
//    }
}
