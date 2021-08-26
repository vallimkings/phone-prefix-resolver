package com.talkdesk.service

object NumberResolverImpl {

    fun normalizeNumber(numbers: List<String>): List<String> =
        validateNumbers(numbers).filter { it.toLongOrNull() != null }

    private fun validateNumbers(numbers: List<String>): List<String> =
        numbers.map {
            it.replace("+", "")
                .replace("\\s".toRegex(), "")
                .replaceFirst(Regex("00"), "")
        }.filter { it.length in 6..13 || it.length == 3 }

}