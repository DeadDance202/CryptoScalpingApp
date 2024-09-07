package com.example.cryptoscalpingapp.presentation.ui.transaction

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoscalpingapp.R
import com.example.cryptoscalpingapp.data.database.local.TransactionItem
import com.example.cryptoscalpingapp.presentation.utils.StringUtils

class TransactionListAdapter : RecyclerView.Adapter<TransactionItemViewHolder>() {

    var transactionList = listOf<TransactionItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionItemViewHolder {
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
            tvTransactionHash.text = StringUtils.transformString(transactionItem.hash)
            tvTokenSymbol.text = transactionItem.tokenSymbol
            tvTokenName.text = transactionItem.tokenName
            tvToAddress.text = StringUtils.transformString(transactionItem.to)
            tvFromAdress.text = StringUtils.transformString(transactionItem.from)
            tvValue.text = transactionItem.value
            tvTimeStamp.text = transactionItem.timeStamp
        }
    }
}