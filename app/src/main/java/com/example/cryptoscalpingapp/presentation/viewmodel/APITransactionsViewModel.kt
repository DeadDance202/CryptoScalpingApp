package com.example.cryptoscalpingapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoscalpingapp.data.database.local.TransactionItem
import com.example.cryptoscalpingapp.data.repository.EtherscanRepository
import com.example.cryptoscalpingapp.domain.entity.ERC20
import com.example.cryptoscalpingapp.domain.usecase.apitransaction.FetchTransactionListUseCase
import com.example.cryptoscalpingapp.domain.usecase.transaction.AddTransactionItemUseCase
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
) : ViewModel() {


    private val client = OkHttpClient()
    private val repository = EtherscanRepository(client)

    //    private val getLastTransactionUseCase = GetLastTransactionUseCase(repository)
    private val fetchTransactionListUseCase = FetchTransactionListUseCase(repository)
    private var cachedTransactions: List<TransactionItem> = emptyList()

    private val addTransactionItemUseCase = AddTransactionItemUseCase(transactionRepository)

    private val fetchJobs = mutableMapOf<String, Job>()

    fun startFetchingTransactionsPeriodically(walletItemId: Int, address: String) {
        val stringId = walletItemId.toString()
        fetchJobs[stringId]?.cancel()
        val requestUrl = ERC20(address = address).buildUrl()
        fetchJobs[stringId] = viewModelScope.launch {
            while (true) {
                fetchLastTransactions(walletItemId, requestUrl)
                delay(3000)
            }
        }
    }

    fun stopFetchingTransactions(id: Int) {
        val stringId = id.toString()
        fetchJobs[stringId]?.cancel()
        fetchJobs.remove(stringId)
    }

    private suspend fun fetchLastTransactions(
        walletItemId: Int,
        url: String
    ): List<TransactionItem> {
        return try {
            val newTransactions = fetchTransactionListUseCase.fetchTransactionList(url)

            val uniqueTransactions = newTransactions.filter { newItem ->
                cachedTransactions.none { it.hash == newItem.hash }
            }

            if (uniqueTransactions.isNotEmpty()) {
                addTransactionItemToDB(uniqueTransactions, walletItemId)
            }
            uniqueTransactions
        } catch (e: Exception) {
            emptyList() // Обработка ошибки
        }
    }

    private suspend fun addTransactionItemToDB(
        transactionItems: List<TransactionItem>,
        walletItemId: Int
    ) {
        for (transactionItem in transactionItems) {
            prepareDataBeforeSave(transactionItem, walletItemId)
            addTransactionItemUseCase.addTransactionItem(transactionItem)
        }
        cachedTransactions = cachedTransactions + transactionItems
    }

    private fun prepareDataBeforeSave(transactionItem: TransactionItem, walletItemId: Int) {
        transactionItem.walletItemId = walletItemId
        transactionItem.value =
            StringUtils.priceFormatting(transactionItem.value, transactionItem.tokenDecimal)
    }
}
