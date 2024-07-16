package com.example.cryptoscalpingapp.presentation.ui.wallet

import androidx.recyclerview.widget.DiffUtil
import com.example.cryptoscalpingapp.data.database.local.WalletItem

class WalletItemDiffCallback: DiffUtil.ItemCallback<WalletItem>() {
    override fun areItemsTheSame(oldItem: WalletItem, newItem: WalletItem): Boolean {
        return  oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: WalletItem, newItem: WalletItem): Boolean {
        return oldItem == newItem
    }
}