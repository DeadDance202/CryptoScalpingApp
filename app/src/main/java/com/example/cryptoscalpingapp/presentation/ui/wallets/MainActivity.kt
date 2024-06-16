package com.example.cryptoscalpingapp.presentation.ui.wallets

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoscalpingapp.R
import com.example.cryptoscalpingapp.presentation.viewmodel.WalletListViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: WalletListViewModel
    private lateinit var walletListAdapter: WalletListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.etherscan_activity_main)
        setupRecyclerView()
        viewModel = ViewModelProvider(this)[WalletListViewModel::class.java]

        viewModel.walletList.observe(this) {
            walletListAdapter.submitList(it)
        }
    }

    private fun setupRecyclerView() {
        val rvWalletList = findViewById<RecyclerView>(R.id.rv_wallet_list)
        walletListAdapter = WalletListAdapter()
        with(rvWalletList) {
            adapter = walletListAdapter
            recycledViewPool.setMaxRecycledViews(
                WalletListAdapter.ENABLED_WALLET_ITEM,
                WalletListAdapter.MAX_POOL_SIZE
            )
            recycledViewPool.setMaxRecycledViews(
                WalletListAdapter.DISABLED_WALLET_ITEM,
                WalletListAdapter.MAX_POOL_SIZE
            )
        }

        setupLongClickListener()
        setupClickListener()
        setupSwipeListener(rvWalletList)
    }

    private fun setupLongClickListener() {
        walletListAdapter.onWalletItemLongClickListener = {
            viewModel.changedEnableStateWalletItem(it)
        }
    }

    private fun setupClickListener() {
        walletListAdapter.onWalletItemClickListener = {
            Log.d("longClick", "$it")
        }
    }

    private fun setupSwipeListener(rvWalletList: RecyclerView) {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = walletListAdapter.currentList[viewHolder.adapterPosition]
                viewModel.removeWalletItem(item)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rvWalletList)
    }
}


//    private lateinit var viewModel: TransactionItemsViewModel
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.etherscan_activity_main)
//        val client = OkHttpClient()
//        val repository = EtherscanRepository(client)
//        val getTransactionsUseCase = GetTransactionListUseCase(repository)
//        val viewModelFactory = TransactionItemsViewModel.TransactionItemsViewModelFactory(getTransactionsUseCase)
//
//        viewModel = ViewModelProvider(this, viewModelFactory)[TransactionItemsViewModel::class.java]
//
//        viewModel.transactions.observe(this
//        ) { transactions ->
//            transactions.forEach {
//                println(it)
//                Log.d("responce!", "$it")
//            }
//        }
//    }
//
//    fun setEtherscanPrefs(view: View?) {
//        val intent = Intent(this, SettingsActivity::class.java)
//        startActivity(intent)
//    }
//}