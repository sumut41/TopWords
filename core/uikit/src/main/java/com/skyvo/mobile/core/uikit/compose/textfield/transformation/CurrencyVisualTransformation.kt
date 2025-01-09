package com.skyvo.mobile.core.uikit.compose.textfield.transformation

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.util.fastJoinToString
import com.skyvo.mobile.core.uikit.util.UI_EMPTY
import com.skyvo.mobile.core.uikit.util.UI_SPACE
import com.skyvo.mobile.core.uikit.compose.textfield.currency.AppCurrencyState

class CurrencyVisualTransformation(
    var currencyState: AppCurrencyState
) : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val inputText: String = text.text

        if (inputText.isEmpty()) {
            return TransformedText(
                text, OffsetMapping.Identity
            )
        }

        val intPart = inputText.substringBeforeLast(currencyState.decimalSeparator)
        val fractionalPart: String =
            inputText.substringAfter(currencyState.decimalSeparator)
        val unitPart =
            if (currencyState.unitPart.isNotEmpty()) String.UI_SPACE.plus(currencyState.unitPart)
            else String.UI_EMPTY

        val formattedText = buildAnnotatedString {
            append(intPart.formatDecimal())
            if (currencyState.fractionDigitCount > 0) {
                append(currencyState.decimalSeparator)
                append(fractionalPart)
            }
            append(unitPart)
        }

        return TransformedText(
            formattedText,
            CurrencyOffsetMapping(
                unmaskedText = inputText,
                maskedText = formattedText.toString(),
                thousandSeparator = currencyState.thousandsSeparator,
                unitPart = unitPart
            )
        )
    }

    private class CurrencyOffsetMapping(
        private val unmaskedText: String,
        private val maskedText: String,
        private val thousandSeparator: Char,
        private val unitPart: String
    ) : OffsetMapping {

        val maskedTextWithoutUnit: String
            get() = maskedText.removeSuffix(unitPart)

        override fun originalToTransformed(offset: Int): Int {
            return (offset + calculateOffsetOriginalTransform(offset))
                .coerceIn(0, maskedTextWithoutUnit.length)
        }

        override fun transformedToOriginal(offset: Int): Int {
            return (offset - calculateOffsetOriginalTransform(offset))
                .coerceIn(0, unmaskedText.length)
        }

        private fun calculateOffsetOriginalTransform(
            offset: Int,
            vararg seperators: Char = charArrayOf(thousandSeparator)
        ): Int {
            val textBeforeOffset =
                unmaskedText.substring(0, offset.coerceIn(0, unmaskedText.length))
            var offsetCounter = 0

            maskedTextWithoutUnit.forEachIndexed { index, c ->
                if (textBeforeOffset.length == (index - offsetCounter)) return offsetCounter
                if (seperators.contains(c)) {
                    offsetCounter += 1
                }
            }

            return offsetCounter
        }
    }

    private fun String.formatDecimal() =
        reversed().chunked(3).fastJoinToString(currencyState.thousandsSeparator.toString()).reversed()
}