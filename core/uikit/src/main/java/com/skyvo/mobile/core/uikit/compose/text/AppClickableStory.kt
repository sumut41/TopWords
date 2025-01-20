package com.skyvo.mobile.core.uikit.compose.text

import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import com.skyvo.mobile.core.uikit.compose.widget.KeyValue
import com.skyvo.mobile.core.uikit.theme.AppTypography
import com.skyvo.mobile.core.uikit.theme.LocalAppColor

@Composable
fun AppClickableStory(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color = LocalAppColor.current.colorTextSubtler,
    words: List<KeyValue>,
    onWordClick: (KeyValue) -> Unit
) {
    val annotatedString = buildAnnotatedString {
        var lastIndex = 0
        val defaultStyle = SpanStyle(color = LocalAppColor.current.colorTextMain)

        words.forEach { word ->
            val startIndex = text.indexOf(word.key.orEmpty(), startIndex = lastIndex, ignoreCase = true)
            if (startIndex != -1) {

                withStyle(style = defaultStyle) {
                    append(text.substring(lastIndex, startIndex))
                }
                pushStringAnnotation(tag = "WORD", annotation = "${word.key}|${word.value}")
                withStyle(
                    style = SpanStyle(
                        color = textColor,
                        textDecoration = TextDecoration.Underline
                    )
                ) {
                    append(word.key)
                }
                pop()
                lastIndex = startIndex + word.key?.length!!
            }
        }
        append(text.substring(lastIndex))
    }


    ClickableText(
        modifier = modifier,
        text = annotatedString,
        style = AppTypography.default.bodyExtraLarge,
        onClick = { offset ->
            annotatedString.getStringAnnotations(tag = "WORD", start = offset, end = offset)
                .firstOrNull()?.let { annotation ->
                    val (key, value) = annotation.item.split("|", limit = 2)
                    onWordClick(KeyValue(key, value))
                }
        }
    )
}