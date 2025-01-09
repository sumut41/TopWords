package com.skyvo.mobile.core.uikit.compose.textfield.transformation

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import com.skyvo.mobile.core.uikit.util.UI_EMPTY

class CreditCardNumberTransformation : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        val formatText = formatCardNumber(text)
        val offsetTranslator = createOffsetTranslator(formatText.text)
        return TransformedText(formatText, offsetTranslator)
    }

    private fun formatCardNumber(text: AnnotatedString): AnnotatedString {
        val trimmedText = text.text.replace("\\s".toRegex(), String.UI_EMPTY)
        val formattedText = buildString {
            trimmedText.forEachIndexed { index, char ->
                if (index > 0 && index % 4 == 0) {
                    append(' ')
                }
                append(char)
            }
        }

        return AnnotatedString(formattedText)
    }

    private fun createOffsetTranslator(text: String): OffsetMapping {
        return object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return offset + countSpacesBeforeOffset(text)
            }

            override fun transformedToOriginal(offset: Int): Int {
                return offset - countSpacesBeforeOffset(text)
            }
        }
    }

    private fun countSpacesBeforeOffset(text: String): Int {
        var spaceCount = 0
        for (element in text) {
            if (element == ' ') {
                spaceCount++
            }
        }
        return spaceCount
    }
}