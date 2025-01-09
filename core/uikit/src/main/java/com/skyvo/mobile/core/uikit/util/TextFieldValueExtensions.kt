package com.skyvo.mobile.core.uikit.util

import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.getTextAfterSelection

fun TextFieldValue.nextCharacter(maxCharCount: Int = 1): String =
    getTextAfterSelection(maxCharCount).toString()

fun TextFieldValue.isCursorAtEnd(): Boolean = (selection.end == text.length)