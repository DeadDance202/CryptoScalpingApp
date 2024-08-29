package com.example.cryptoscalpingapp.presentation.utils


import java.text.DecimalFormat

object StringUtils {
    fun transformString(string: String, maxLength: Int = 8): String {
        return if (string.length <= maxLength) {
            string
        } else {
            val prefix = string.take(4)
            val suffix = string.takeLast(4)
            "$prefix...$suffix"
        }
    }

    fun priceFormatting(value: String, priceDecimal: String): String {
        val positionFromEnd = priceDecimal.toInt()
        if (positionFromEnd < 0 || positionFromEnd > value.length) {
            return value
        }
        val insertionIndex = value.length - positionFromEnd
        var integerPartPrice = value.substring(0, insertionIndex)
        val decimalPartPrice = value.substring(insertionIndex)
        val number = integerPartPrice.toLong()

        val decimalFormat = DecimalFormat("#,###")
        integerPartPrice = decimalFormat.format(number)

        return "$integerPartPrice.$decimalPartPrice"
    }
}