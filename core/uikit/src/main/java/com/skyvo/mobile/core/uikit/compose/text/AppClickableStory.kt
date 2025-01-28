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
        val matches = mutableListOf<Pair<Int, KeyValue>>()

        // Tüm kelimelerin eşleşmelerini bul ve başlangıç indekslerine göre sıralama için listeye ekle
        words.forEach { word ->
            val wordKey = word.key.orEmpty()
            var startIndex = text.indexOf(wordKey, ignoreCase = true)
            while (startIndex != -1) {
                matches.add(startIndex to word)
                startIndex = text.indexOf(wordKey, startIndex + 1, ignoreCase = true)
            }
        }

        // Bulunan tüm eşleşmeleri başlangıç indeksine göre sırala
        matches.sortBy { it.first }

        var lastIndex = 0
        val defaultStyle = SpanStyle(color = LocalAppColor.current.colorTextMain)

        matches.forEach { (startIndex, word) ->
            // Eğer `startIndex` `lastIndex`'ten küçükse, işleme devam etmeden önce güncelleme yap
            if (startIndex >= lastIndex) {
                // Önceki normal metni ekle
                withStyle(style = defaultStyle) {
                    append(text.substring(lastIndex, startIndex))
                }

                // Kelimeyi işaretle ve stil uygula
                pushStringAnnotation(tag = "WORD", annotation = "${word.key}|${word.value}")
                withStyle(
                    style = SpanStyle(
                        color = textColor,
                        textDecoration = TextDecoration.Underline
                    )
                ) {
                    append(word.key.orEmpty())
                }
                pop()

                // İşlenmiş kısmı güncelle
                lastIndex = startIndex + (word.key?.length ?: 0)
            }
        }

        // Kalan metni ekle
        if (lastIndex < text.length) {
            append(text.substring(lastIndex))
        }
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