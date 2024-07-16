package com.example.cryptoscalpingapp.data.database.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wallets")
data class WalletItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val address: String,
    val shortAddress: String,
    val enabled: Boolean
) {
    companion object {
        const val UNDEFINED_ID = -1
    }
}
