package com.example.cryptoscalpingapp.presentation.ui.wallet

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cryptoscalpingapp.R
import com.example.cryptoscalpingapp.data.database.local.WalletItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WalletItemActivity : AppCompatActivity() {

    private var screenMode = MODE_UNKNOWN
    private var walletItemId = WalletItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallet_item)
        parseIntent()
        if (savedInstanceState == null) {
            launchRightMode()
        }
    }

    private fun launchRightMode() {
        val fragment = when (screenMode) {
            MODE_EDIT -> WalletItemFragment.newInstanceEditItem(walletItemId)
            MODE_ADD -> WalletItemFragment.newInstanceAddItem()
            else -> throw RuntimeException(("Unknown screen mode: $screenMode"))
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.wallet_item_container, fragment)
            .commit()
    }

    private fun parseIntent() {
        if (!intent.hasExtra(SCREEN_MODE)) {
            throw RuntimeException("Screen mode is absent ")
        }

        val mode = intent.getStringExtra(SCREEN_MODE)

        if (mode != MODE_EDIT && mode != MODE_ADD) {
            throw RuntimeException("Unknown screen mode $mode")
        }
        screenMode = mode

        if (screenMode == MODE_EDIT) {
            if (!intent.hasExtra(WALLET_ITEM_ID)) {
                throw RuntimeException("Param wallet item id is absent ")
            }
            walletItemId = intent.getIntExtra(WALLET_ITEM_ID, -1)
        }
    }

    companion object {
        private const val SCREEN_MODE = "extra_mode"
        private const val WALLET_ITEM_ID = "extra_wallet_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = ""

        fun newIntentEditItem(context: Context, walletItemId: Int): Intent {
            val intent = Intent(context, WalletItemActivity::class.java)
            intent.putExtra(SCREEN_MODE, MODE_EDIT)
            intent.putExtra(WALLET_ITEM_ID, walletItemId)
            return intent
        }

        fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(context, WalletItemActivity::class.java)
            intent.putExtra(SCREEN_MODE, MODE_ADD)
            return intent
        }
    }
}