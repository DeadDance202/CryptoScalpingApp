package com.example.cryptoscalpingapp.presentation.ui.transaction

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoscalpingapp.R


class TransactionItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val tvTokenName = view.findViewById<TextView>(R.id.tv_token_name)
    val tvTokenSymbol = view.findViewById<TextView>(R.id.tv_token_symbol)
    val tvTransactionHash = view.findViewById<TextView>(R.id.tv_transaction_hash)
    val tvTimeStamp = view.findViewById<TextView>(R.id.tv_timeStamp)
    val tvFromAdress = view.findViewById<TextView>(R.id.tv_fromAddress)
    val tvToAddress = view.findViewById<TextView>(R.id.tv_toAddress)
    val tvValue = view.findViewById<TextView>(R.id.tv_value)

}