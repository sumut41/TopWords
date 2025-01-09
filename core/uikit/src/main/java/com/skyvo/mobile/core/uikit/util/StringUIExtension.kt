package com.skyvo.mobile.core.uikit.util

import com.skyvo.mobile.core.uikit.compose.textfield.currency.AppCurrencyState
import java.util.Locale

const val DATE_FORMAT = "dd.MM.yyyy"
const val MASK_DATE_FORMAT = "##/##/####"
const val MASK_DEFAULT = "### ### ## ##"
const val MASK_CHAR = "#"
val String.Companion.UI_EMPTY by lazy { "" }
val String.Companion.UI_SPACE by lazy { " " }
val String.Companion.UI_ZERO by lazy { "0" }

fun String?.addBetweenInsertSpace(index: Int): String {
    val result = StringBuilder()
    this?.forEachIndexed {i , char ->
        if (i % index == 0 && i > 0 && char != ' ') {
            result.append(' ')
        }
        result.append(char)
    }
    return result.toString()
}

fun String.uppercaseTurkish(): String {
    return this.uppercase(Locale("tr"))
}

fun String.notContainsClearCharacters(): Boolean {
    val regex = Regex("[A-Za-z0-9ıİğĞüÜşŞöÖçÇ]")
    return regex.containsMatchIn(this)
}

fun String.formatMask(mask: String = MASK_DEFAULT, maskChar: String = MASK_CHAR): String {
    if (this.isEmpty()) {
        return this
    }

    val numbers = this.replace("[^0-9]".toRegex(), "")

    val result = StringBuilder("")
    var index = 0

    mask.forEach { c->
        if (c.toString() == maskChar && index <= numbers.lastIndex) {
            result.append(numbers[index])
            index++
        } else {
            result.append(c.toString())
        }
    }

    return result.toString()
}

fun String.addMaskFractionalPart(state: AppCurrencyState): String =
    if (state.fractionDigitCount == 0) this.substringBefore(state.decimalSeparator)
    else if (this.contains(state.decimalSeparator)) {
        with(substringAfter(state.decimalSeparator)) {
            if (this@with.length < state.fractionDigitCount) {
                this@addMaskFractionalPart.addPrefix(kotlin.String.UI_ZERO.repeat(state.fractionDigitCount - this@with.length))
            } else {
                "${this@addMaskFractionalPart.substringBefore(state.decimalSeparator)}${state.decimalSeparator}${
                    this@addMaskFractionalPart.substringAfter(state.decimalSeparator)
                        .substring(0, state.fractionDigitCount)
                }"
            }
        }
    } else {
        addSuffix("${state.decimalSeparator}${String.UI_ZERO}${String.UI_ZERO}")
    }

fun String.addMaskInPart(state: AppCurrencyState): String =
    if (isEmpty() || startsWith("${state.decimalSeparator}")) addPrefix(String.UI_ZERO)
    else if (startsWith("0${state.decimalSeparator}")) this
    else if (this.toCurrency() != 0.0 && startsWith(String.UI_ZERO)) removePrefix(String.UI_ZERO)
    else if (this.toCurrency() == 0.0) {
        addPrefix(String.UI_ZERO)
    }
    else {
        this
    }

fun String.addPrefix(prefix: String): String {
    return java.lang.StringBuilder().append(prefix).append(this).toString()
}

fun String.addSuffix(suffix: String, addSpace: Boolean = false): String {
    return if (addSpace) {
        java.lang.StringBuilder().append(this).append(String.UI_SPACE).append(suffix).toString()
    } else {
        java.lang.StringBuilder().append(this).append(suffix).toString()
    }
}

fun String?.toCurrency(): Double? =
    if (this?.firstOrNull() == ',' && this.length == 3) {
        String.UI_ZERO.plus(this).replace(',', '.').toDoubleOrNull()
    } else {
        this?.replace(',', '.')?.toDoubleOrNull()
    }

fun String.checkDecimalSeparatorDeleted(
    decimalSeparator: Char,
    previousText: String
): Boolean =
    !contains(decimalSeparator) && previousText.contains(decimalSeparator) && previousText.replace(
        decimalSeparator.toString(),
        String.UI_EMPTY
    ) == this

fun String.checkDecimalSeparatorSecondPressed(
    decimalSeparator: Char,
    previousText: String
): Boolean =
    count { it == decimalSeparator } == 2 && previousText.count { it == decimalSeparator } == 1

fun String.checkFractionPartChanged(
    state: AppCurrencyState,
    previousText: String
): Boolean =
    if (contains(state.decimalSeparator) && previousText.contains(
            state.decimalSeparator
        )
    ) {
        val decimalPart = substringAfter(state.decimalSeparator)
        val decimalPartForPre = previousText.substringAfter(state.decimalSeparator)
        decimalPartForPre.length == state.fractionDigitCount && decimalPart != decimalPartForPre
    } else {
        false
    }

fun String.applyFractionPartChange(
    state: AppCurrencyState,
    isCursorAtEnd: Boolean,
    previousText: String
): String {
    return if (contains(state.decimalSeparator) && previousText.contains(
        state.decimalSeparator
    )
        ) {
        val intPart = substringBefore(state.decimalSeparator)
        val decimalPart = substringAfter(state.decimalSeparator)
        val decimalPartForPre = previousText.substringAfter(state.decimalSeparator)
        val tempDecimalPart = if (decimalPart.length == decimalPartForPre.length) {
            decimalPart
        } else if (decimalPart.length > decimalPartForPre.length) {
            var isChanged = false
            decimalPartForPre.mapIndexed { index, c ->
                val decimalChar = decimalPart[index]
                if (decimalChar != c && !isChanged) {
                    isChanged = true
                    decimalChar
                } else {
                    c
                }
            }.joinToString(String.UI_EMPTY)
        } else if (decimalPart.length == 1) {
            if (isCursorAtEnd) decimalPart.plus(String.UI_ZERO)
            else String.UI_ZERO.plus(decimalPart)
        } else {
            if (decimalPart == decimalPartForPre.firstOrNull()?.toString())
                decimalPart.plus(String.UI_ZERO)
            else decimalPart
        }
        if (intPart.isEmpty()) {
            intPart.plus(String.UI_ZERO)
        }
        intPart.plus(String.UI_ZERO)
    } else {
        this
    }
}

fun String.removeDotAndComma() =
    this.replace(".", "").replace(",", "")

fun String.containsDotOrComma() =
    this.contains(".") || this.contains(",")