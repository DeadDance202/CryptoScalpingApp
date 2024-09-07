package com.example.cryptoscalpingapp.domain.entity

data class ERC20(
    val etherscanUrl: String = "https://api.etherscan.io/api",
    val address: String,
    val page: String = "1",
    val offset: String = "1",
    val sort: String = "desc",
    val startBlock: String = "0",
    val endBlock: String = "999999999",
    private val apiKeyProvider: () -> String?
) {
    fun buildUrl(): String {
        val apiKey = apiKeyProvider.invoke() ?: ""
        return "$etherscanUrl?module=account" +
                "&action=tokentx" +
                "&address=$address" +
                "&page=$page" +
                "&offset=$offset" +
                "&startblock=$startBlock" +
                "&endblock=$endBlock" +
                "&sort=$sort" +
                "&apikey=$apiKey"
    }
}
