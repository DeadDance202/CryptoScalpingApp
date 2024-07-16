package com.example.cryptoscalpingapp.presentation.ui.wallet

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoscalpingapp.R

class WalletItemViewHolder(val view: View): RecyclerView.ViewHolder(view) {
    var tvAddressName = view.findViewById<TextView>(R.id.tv_address_name)
    var tvAddress = view.findViewById<TextView>(R.id.tv_address)
}