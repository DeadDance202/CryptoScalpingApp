package com.example.cryptoscalpingapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoscalpingapp.data.database.local.TransactionItem
import com.example.cryptoscalpingapp.data.repository.EtherscanRepository
import com.example.cryptoscalpingapp.domain.entity.ERC20
import com.example.cryptoscalpingapp.domain.usecase.apitransaction.FetchTransactionListUseCase
import com.example.cryptoscalpingapp.domain.usecase.transaction.AddTransactionItemUseCase
import com.example.cryptoscalpingapp.domain.usecase.transaction.GetTransactionListUseCase
import com.example.cryptoscalpingapp.domain.usecase.transaction.TransactionListRepository
import com.example.cryptoscalpingapp.presentation.utils.StringUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import javax.inject.Inject

@HiltViewModel
class APITransactionsViewModel @Inject constructor(
    private val transactionRepository: TransactionListRepository
): ViewModel() {

    private var requestUrl = ""
    private val client = OkHttpClient()
    private val repository = EtherscanRepository(client)
//    private val getLastTransactionUseCase = GetLastTransactionUseCase(repository)
    private val fetchTransactionListUseCase = FetchTransactionListUseCase(repository)
    private var cachedTransactions: List<TransactionItem> = emptyList()

    private val getTransactionListUseCase = GetTransactionListUseCase(transactionRepository)
    private val addTransactionItemUseCase = AddTransactionItemUseCase(transactionRepository)

    init {
        viewModelScope.launch {
            cachedTransactions = getTransactionListUseCase.getTransactionList().value ?: run {
                // Обработка, если значение null (например, присвоить пустой список)
                emptyList()
            }
        }
    }

    fun startFetchingTransactionsPeriodically(address: String) {
        requestUrl = ERC20(address = address).buildUrl()
//        Log.d("requestUrl", requestUrl)
        viewModelScope.launch {
            while (true) {
                val list = fetchLastTransactions(requestUrl)
                for (item in list) {
                    Log.d("trans_list", item.toString())
                }

                delay(3000)
            }
        }
    }

//    suspend fun getLastTransaction(): TransactionItem? {
//        return getLastTransactionUseCase.getLastTransaction()
//    }

    private suspend fun fetchLastTransactions(url: String): List<TransactionItem> {
        return try {
            val newTransactions = fetchTransactionListUseCase.fetchTransactionList(url)

            val uniqueTransactions = newTransactions.filter { newItem ->
                cachedTransactions.none { it.hash == newItem.hash }
            }

            if (uniqueTransactions.isNotEmpty()) {
                addTransactionItemToDB(uniqueTransactions)
            }
            uniqueTransactions
        } catch (e: Exception) {
            emptyList() // Обработка ошибки
        }
    }

    private suspend fun addTransactionItemToDB(transactionItems: List<TransactionItem>) {
        for (transactionItem in transactionItems) {
            prepareDataBeforeSave(transactionItem)
            addTransactionItemUseCase.addTransactionItem(transactionItem)
        }
        cachedTransactions = cachedTransactions + transactionItems
    }

    private fun prepareDataBeforeSave(transactionItem: TransactionItem) {
        transactionItem.value = StringUtils.priceFormatting(transactionItem.value, transactionItem.tokenDecimal)
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
