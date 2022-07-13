package com.odds.oddsbooking.utils

object NameUtilities {
    fun getNameFormatter(name: String): String {
        val nameFormatter = name.lowercase().trim().split("\\s+".toRegex()).toMutableList()
        for (index in nameFormatter.indices) {
            nameFormatter[index] = nameFormatter[index].replaceFirstChar { it.uppercaseChar() }
        }
        return nameFormatter.joinToString(" ")
    }
}