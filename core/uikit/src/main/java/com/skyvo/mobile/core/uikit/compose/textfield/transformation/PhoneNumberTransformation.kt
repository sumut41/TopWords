package com.skyvo.mobile.core.uikit.compose.textfield.transformation

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class PhoneNumberTransformation: VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        return formatPhoneNumber(text)
    }

    private fun formatPhoneNumber(number: AnnotatedString): TransformedText {
        val trimmed = if (number.text.length >= 10) {
            number.text.substring(0..9)
        } else {
            number.text
        }

        var out = ""
        for (i in trimmed.indices) {
            if (i == 0) out += "("
            out += trimmed[i]
            if (i == 2) out += ")"
            if (i == 2 || i == 5 || i == 7) out += " "
        }

        val numberOffsetTranslator = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                if (offset <= 0) return offset
                if (offset <= 2) return offset + 1
                if (offset <= 5) return offset + 3
                if (offset <= 7) return offset + 4
                if (offset <= 9) return offset + 5
                return 15
            }

            override fun transformedToOriginal(offset: Int): Int {
                if (offset <= 0) return offset
                if (offset <= 3) return offset - 1
                if (offset <= 8) return offset - 3
                if (offset <= 11) return offset - 4
                if (offset <= 14) return offset - 5
                return 10
            }
        }

        return TransformedText(AnnotatedString(out), numberOffsetTranslator)
    }
}