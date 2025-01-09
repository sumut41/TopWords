package com.skyvo.mobile.core.uikit.compose.textfield.transformation

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class IBANNumberTransformation(private val prefix: String): VisualTransformation {

    private var ibanText: String = ""

    override fun filter(text: AnnotatedString): TransformedText {
        val formatText = formatIBANNumber(text)
        val offsetTranslator = createOffsetTranslator()
        return TransformedText(formatText, offsetTranslator)
    }

    private fun formatIBANNumber(iban: AnnotatedString): AnnotatedString {
        ibanText = iban.text
        val trimmed = if (iban.text.length > 24) iban.text.substring(0..23) else iban.text
        val out =
            if (trimmed.isNotEmpty()) "$prefix$trimmed".replace("....".toRegex(), "$0 ") else prefix
        return AnnotatedString(out)
    }

    private fun createOffsetTranslator(): OffsetMapping {
        return object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return if (offset <= 1) {
                    offset + 2
                } else if (offset <= 5) {
                    offset + 3
                } else if (offset <= 9) {
                    offset + 4
                }  else if (offset <= 13) {
                    offset + 5
                }  else if (offset <= 17) {
                    offset + 6
                } else if (offset <= 21) {
                    offset + 7
                }  else if (offset <= 24) {
                    offset + 8
                } else {
                    32
                }
            }

            override fun transformedToOriginal(offset: Int): Int {
                return if (offset <= 1) {
                    if (ibanText.isEmpty()) {
                        0
                    } else {
                        offset
                    }
                } else if (offset <= 3) {
                    offset - 2
                } else if (offset <= 8) {
                    offset - 3
                }  else if (offset <= 13) {
                    offset - 4
                }  else if (offset <= 18) {
                    offset - 5
                } else if (offset <= 23) {
                    offset - 6
                }  else if (offset <= 28) {
                    offset - 7
                }  else if (offset <= 31) {
                    offset - 8
                } else {
                    24
                }
            }
        }
    }
}