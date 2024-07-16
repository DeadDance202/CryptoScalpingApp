package com.example.cryptoscalpingapp.presentation.ui.transaction

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoscalpingapp.R
import com.example.cryptoscalpingapp.data.database.local.TransactionItem

class TransactionListAdapter : RecyclerView.Adapter<TransactionItemViewHolder>() {

    var transactionList = listOf<TransactionItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionItemViewHolder {
//        var layout = when (viewType) {
//            WalletListAdapter.ENABLED_WALLET_ITEM -> R.layout.wallet_address_enabled
//            WalletListAdapter.DISABLED_WALLET_ITEM -> R.layout.wallet_address_disabled
//            else -> throw RuntimeException("Unknown view type: $viewType")
//        }
        val layout = R.layout.transaction_item
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return TransactionItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return transactionList.size
    }

    override fun onBindViewHolder(viewHolder: TransactionItemViewHolder, position: Int) {
        val transactionItem = transactionList[position]
        with(viewHolder) {
            tvTransactionHash.text = transactionItem.hash
            tvTokenSymbol.text = transactionItem.tokenSymbol
            tvTokenName.text = transactionItem.tokenName
            tvToAddress.text = transactionItem.to
            tvFromAdress.text = transactionItem.from
            tvValue.text = transactionItem.value
            tvTimeStamp.text = transactionItem.timeStamp
        }
    }

}