package com.skyvo.mobile.core.uikit.compose.textfield.currency

import com.skyvo.mobile.core.uikit.util.UI_EMPTY
import com.skyvo.mobile.core.uikit.util.UI_ZERO
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

data class AppCurrencyState (
    val currencyUnit: String = "TL",
    val locale: Locale = Locale("tr-TR"),
    val maxValue: Double = Double.MAX_VALUE,
    val hideFractionPart: Boolean = false,
    val placeHolderText: String? = null
) {
    companion object {
        const val DEFAULT_FRACTION_COUNT = 2
        const val HIDE_FRACTION_COUNT = 0
        const val DOT = '.'
        const val COMMA = ','
    }

    private val decimalFormat by lazy {
        DecimalFormat(locale.language).apply {
            maximumFractionDigits =
                if (hideFractionPart) HIDE_FRACTION_COUNT else this.currency?.defaultFractionDigits
                    ?: DEFAULT_FRACTION_COUNT
            decimalFormatSymbols = DecimalFormatSymbols.getInstance(locale).apply {
                groupingSeparator = DOT
                decimalSeparator = COMMA
            }
            maximumFractionDigits = maxValue.toString().substringBefore(DOT).length
        }
    }

    val thousandsSeparator
        get() = decimalFormat.decimalFormatSymbols.groupingSeparator

    val decimalSeparator
        get() = decimalFormat.decimalFormatSymbols.decimalSeparator

    val fractionDigitCount
        get() = if (hideFractionPart) HIDE_FRACTION_COUNT else decimalFormat.maximumFractionDigits

    val defaultFractionPart = (String.UI_ZERO.repeat(fractionDigitCount))

    val defaultIntPart = String.UI_ZERO

    val unitPart = currencyUnit

    val placeHolder = placeHolderText
        ?: (defaultIntPart +
                "${if (defaultFractionPart.isEmpty()) String.UI_EMPTY else decimalSeparator}" +
                defaultFractionPart +
                if (unitPart.isEmpty()) String.UI_EMPTY else " $unitPart")
}