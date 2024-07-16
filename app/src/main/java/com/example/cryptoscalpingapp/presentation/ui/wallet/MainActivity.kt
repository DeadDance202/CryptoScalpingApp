package com.example.cryptoscalpingapp.presentation.ui.wallet

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoscalpingapp.R
import com.example.cryptoscalpingapp.presentation.ui.transaction.TransactionListActivity
import com.example.cryptoscalpingapp.presentation.viewmodel.APITransactionsViewModel
import com.example.cryptoscalpingapp.presentation.viewmodel.WalletListViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: WalletListViewModel by viewModels()
    private lateinit var walletListAdapter: WalletListAdapter
    private var walletItemContainer: FragmentContainerView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        walletItemContainer = findViewById(R.id.wallet_item_container)
        setupRecyclerView()
        viewModel.walletList.observe(this) {
            walletListAdapter.submitList(it)
        }
        val buttonAddWalletItem = findViewById<FloatingActionButton>(R.id.btn_add_wallet_item)
        buttonAddWalletItem.setOnClickListener {
            if (isOnePaneMode()) {
                val intent = WalletItemActivity.newIntentAddItem(this)
                startActivity(intent)
            } else {
                launchFragment(WalletItemFragment.newInstanceAddItem())
            }
        }
    }

    private fun isOnePaneMode(): Boolean {
        return walletItemContainer == null
    }

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.wallet_item_container, fragment)
            .addToBackStack(null)
            .commit()
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
            val transactionViewModel =
                ViewModelProvider(this)[APITransactionsViewModel::class.java]
            lifecycleScope.launch {
                viewModel.changedEnableStateWalletItem(it)
                if (!it.enabled) {
                    transactionViewModel.startFetchingTransactionsPeriodically(it.address)
                }
            }
        }
    }

    private fun setupClickListener() {
        walletListAdapter.onWalletItemClickListener = {
            if (isOnePaneMode()) {
                val intent = WalletItemActivity.newIntentEditItem(this, it.id)
                startActivity(intent)
            } else {
                launchFragment(WalletItemFragment.newInstanceEditItem(it.id))
            }
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
                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        viewModel.removeWalletItem(item)
                    }

                    ItemTouchHelper.RIGHT -> {
                        val context = rvWalletList.context
                        val intent = TransactionListActivity.newIntent(context)

//                        val intent = Intent(context, TransactionListActivity::class.java)
//                            .apply {
//                            putExtra("item_id", item.id)
//                        }
                        context.startActivity(intent)
                        // Можно добавить восстановление положения элемента
                        walletListAdapter.notifyItemChanged(viewHolder.adapterPosition)
                    }
                }
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