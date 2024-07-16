package com.example.cryptoscalpingapp.presentation.ui.wallet

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.ListAdapter
import com.example.cryptoscalpingapp.R
import com.example.cryptoscalpingapp.data.database.local.WalletItem

class WalletListAdapter : ListAdapter<WalletItem, WalletItemViewHolder>(WalletItemDiffCallback()) {

    var onWalletItemClickListener: ((WalletItem) -> Unit)? = null
    var onWalletItemLongClickListener: ((WalletItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WalletItemViewHolder {
        val layout = when (viewType) {
            ENABLED_WALLET_ITEM -> R.layout.wallet_address_enabled
            DISABLED_WALLET_ITEM -> R.layout.wallet_address_disabled
            else -> throw RuntimeException("Unknown view type: $viewType")
        }
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return WalletItemViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        val walletItem = getItem(position)
        return if (walletItem.enabled) ENABLED_WALLET_ITEM else DISABLED_WALLET_ITEM
    }

    override fun onBindViewHolder(viewHolder: WalletItemViewHolder, position: Int) {
        val walletItem = getItem(position)
        with(viewHolder) {
            view.setOnClickListener() {
                onWalletItemClickListener?.invoke(walletItem)
                true
            }

            view.setOnLongClickListener() {
                val text = if (!walletItem.enabled) "Notify is enabled" else "Notify is disabled"
                val duration = Toast.LENGTH_SHORT
                Toast.makeText(view.context, text, duration).show()

                onWalletItemLongClickListener?.invoke(walletItem)
                true
            }

            tvAddressName.text = walletItem.name
            tvAddress.text = walletItem.shortAddress
        }
    }

    companion object {
        const val ENABLED_WALLET_ITEM = 1
        const val DISABLED_WALLET_ITEM = 0
        const val MAX_POOL_SIZE = 15
    }
}