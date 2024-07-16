package com.example.cryptoscalpingapp.presentation.ui.transaction

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoscalpingapp.R
import com.example.cryptoscalpingapp.presentation.viewmodel.TransactionItemViewModel

class TransactionListActivity : AppCompatActivity() {
    private lateinit var viewModel: TransactionItemViewModel
    private lateinit var adapter: TransactionListAdapter
    private var fileName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        TransactionListRepositoryImpl.init(this)
        setContentView(R.layout.activity_transaction_list)
        parseIntent()
        setupRecyclerView()
        viewModel = ViewModelProvider(this)[TransactionItemViewModel::class.java]
        viewModel.getTransactionListFromFile(fileName)
        viewModel.transactionList.observe(this) {
            adapter.transactionList = it
        }

//        viewModel.walletList.observe(this) {
//            walletListAdapter.submitList(it)
//        }
    }

    private fun parseIntent() {
        if (!intent.hasExtra("fileName")) {
            throw RuntimeException("file not found")
        }

        val name = intent.getStringExtra("fileName")
        if (name != null) {
            fileName = name
        }
    }

    private fun setupRecyclerView() {
        val rvTransactionList = findViewById<RecyclerView>(R.id.rv_transaction_list)
        adapter = TransactionListAdapter()
        rvTransactionList.adapter = adapter
        rvTransactionList.layoutManager = LinearLayoutManager(this)
    }

    companion object {
        fun newIntent(context: Context): Intent {
            val intent = Intent(context, TransactionListActivity::class.java)
            return intent
        }
    }
}