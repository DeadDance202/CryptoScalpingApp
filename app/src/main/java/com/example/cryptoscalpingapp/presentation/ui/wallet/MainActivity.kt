package com.example.cryptoscalpingapp.presentation.ui.wallet

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoscalpingapp.R
import com.example.cryptoscalpingapp.data.database.local.WalletItem
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
            launchFetchingTransactionsForEnabledItems(it)
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

    private fun launchFetchingTransactionsForEnabledItems(walletList: List<WalletItem>) {
        walletList.forEach { walletItem ->
            processFetchingTransactions(walletItem)
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
        walletListAdapter.onWalletItemLongClickListener = { walletItem ->
            lifecycleScope.launch {
                val updatedItem = viewModel.changedEnableStateWalletItem(walletItem)
                processFetchingTransactions(updatedItem)
            }
        }
    }

    private fun processFetchingTransactions(walletItem: WalletItem) {
        val apiTransactionsViewModel: APITransactionsViewModel by viewModels()
        if (walletItem.enabled) {
            lifecycleScope.launch {
                apiTransactionsViewModel.startFetchingTransactionsPeriodically(
                    walletItem.id,
                    walletItem.address
                )
            }
        } else {
            apiTransactionsViewModel.stopFetchingTransactions(walletItem.id)
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
                        val intent = TransactionListActivity.newIntent(context, item.id)
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