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
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import javax.inject.Inject

@HiltViewModel
class APITransactionsViewModel @Inject constructor(
    private val transactionRepository: TransactionListRepository
): ViewModel() {


    private val client = OkHttpClient()
    private val repository = EtherscanRepository(client)
//    private val getLastTransactionUseCase = GetLastTransactionUseCase(repository)
    private val fetchTransactionListUseCase = FetchTransactionListUseCase(repository)
    private var cachedTransactions: List<TransactionItem> = emptyList()

    private val getTransactionListUseCase = GetTransactionListUseCase(transactionRepository)
    private val addTransactionItemUseCase = AddTransactionItemUseCase(transactionRepository)

    private val fetchJobs = mutableMapOf<String, Job>()

    init {
        viewModelScope.launch {
            cachedTransactions = getTransactionListUseCase.getTransactionList().value ?: run {
                emptyList()
            }
        }
    }

    fun startFetchingTransactionsPeriodically(id: Int, address: String) {
        val stringId = id.toString()
        fetchJobs[stringId]?.cancel()
        val requestUrl = ERC20(address = address).buildUrl()
//        Log.d("requestUrl", requestUrl)
        Log.d("job", fetchJobs.toString())
        fetchJobs[stringId] = viewModelScope.launch {
            while (true) {
//                val list = fetchLastTransactions(requestUrl)
//                for (item in list) {
//                    Log.d("trans_list", item.toString())
//                }
                fetchLastTransactions(requestUrl)
                delay(3000)
            }
        }
    }

    fun stopFetchingTransactions(id: Int) {
        val stringId = id.toString()
        fetchJobs[stringId]?.cancel()
        fetchJobs.remove(stringId)
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
