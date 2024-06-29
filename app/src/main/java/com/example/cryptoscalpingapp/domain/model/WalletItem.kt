package com.example.cryptoscalpingapp.domain.model

data class WalletItem(
    val name: String,
    val address: String,
    val shortAddress: String,
    val enabled: Boolean,
    var id: Int = UNDEFINED_ID
) {
    companion object {
        const val UNDEFINED_ID = -1
    }
}
