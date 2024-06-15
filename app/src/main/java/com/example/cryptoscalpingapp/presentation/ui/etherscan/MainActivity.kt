package com.example.cryptoscalpingapp.presentation.ui.etherscan

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.cryptoscalpingapp.R
import com.example.cryptoscalpingapp.data.repository.EtherscanRepository
import com.example.cryptoscalpingapp.domain.usecase.GetTransactionItemListUseCase
import com.example.cryptoscalpingapp.presentation.viewmodel.TransactionItemsViewModel
import okhttp3.OkHttpClient


class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: TransactionItemsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.etherscan_activity_main)
        val client = OkHttpClient()
        val repository = EtherscanRepository(client)
        val getTransactionsUseCase = GetTransactionItemListUseCase(repository)
        val viewModelFactory = TransactionItemsViewModel.TransactionItemsViewModelFactory(getTransactionsUseCase)

        viewModel = ViewModelProvider(this, viewModelFactory)[TransactionItemsViewModel::class.java]

        viewModel.transactions.observe(this
        ) { transactions ->
            transactions.forEach {
                println(it)
                Log.d("responce!", "$it")
            }
        }
    }

    fun setEtherscanPrefs(view: View?) {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }
}