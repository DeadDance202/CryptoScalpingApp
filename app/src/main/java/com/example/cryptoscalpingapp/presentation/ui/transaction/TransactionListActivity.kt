package com.example.cryptoscalpingapp.presentation.ui.transaction

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoscalpingapp.R
import com.example.cryptoscalpingapp.presentation.viewmodel.TransactionItemViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TransactionListActivity : AppCompatActivity() {
    private val viewModel: TransactionItemViewModel by viewModels()
    private lateinit var adapter: TransactionListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_list)
        val walletItemId = intent.getIntExtra(EXTRA_WALLET_ITEM_ID, -1)
        setupRecyclerView()
        if (walletItemId != -1) {
            viewModel.getTransactionListByWalletId(walletItemId)
        }
        viewModel.transactionList.observe(this) {
            adapter.transactionList = it
        }
    }

    private fun setupRecyclerView() {
        val rvTransactionList = findViewById<RecyclerView>(R.id.rv_transaction_list)
        adapter = TransactionListAdapter()
        rvTransactionList.adapter = adapter
        rvTransactionList.layoutManager = LinearLayoutManager(this)
    }

    companion object {
        fun newIntent(context: Context, walletItemId: Int): Intent {
            val intent = Intent(context, TransactionListActivity::class.java)
            intent.putExtra(EXTRA_WALLET_ITEM_ID, walletItemId)
            return intent
        }

        private const val EXTRA_WALLET_ITEM_ID = "wallet_item_id"
    }
}