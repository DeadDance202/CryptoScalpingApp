package com.example.cryptoscalpingapp.presentation.utils

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
}