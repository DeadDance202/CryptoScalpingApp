package com.example.cryptoscalpingapp.domain.entity

data class ERC20(
    val etherscanUrl: String = "https://api.etherscan.io/api",
    val address: String,
    val page: String = "1",
    val offset: String = "2",
    val sort: String = "asc",
    val apiKey: String = "PSJW6F99HTNACN1ENWVCTZZ2P1U2I9AU9Q",
    val startBlock: String = "0",
    val endBlock: String = "999999999"
) {
    fun buildUrl(): String {
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
