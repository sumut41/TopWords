package com.skyvo.mobile.core.uikit.compose.textfield.transformation

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.substring

class DateTransformation(val seperator: String = "/") : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = if (text.text.length >= 8) {
            text.text.substring(0..7)
        } else {
            text.text
        }
        var output = ""
        for (i in trimmed.indices) {
            output += trimmed[i]
            if (i % 2 == 1 && i < 4) {
                output += seperator
            }
        }

        val dateTranslator = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                if (offset <= 1) return offset
                if (offset <= 3) return offset + 1
                if (offset <= 7) return offset + 2
                return 10
            }

            override fun transformedToOriginal(offset: Int): Int {
                if (offset <= 1) return offset
                if (offset <= 4) return offset - 1
                if (offset <= 9) return offset - 2
                return 8
            }
        }

        return TransformedText(AnnotatedString(output), dateTranslator)
    }
}