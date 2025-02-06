package com.skyvo.mobile.core.uikit.compose.text

import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import com.skyvo.mobile.core.uikit.compose.widget.KeyValue
import com.skyvo.mobile.core.uikit.theme.AppPrimaryTheme
import com.skyvo.mobile.core.uikit.theme.AppTypography
import com.skyvo.mobile.core.uikit.theme.LocalAppColor

@Composable
fun AppClickableStory(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color = LocalAppColor.current.colorTextSubtler,
    words: List<KeyValue>,
    sentences: List<String>,
    onWordClick: (KeyValue) -> Unit,
    onSentenceClick: (String) -> Unit
) {
    AppPrimaryTheme {
        var selectedWord by remember { mutableStateOf<String?>(null) }

        val annotatedString = buildAnnotatedString {
            val wordMatches = mutableListOf<Pair<Int, KeyValue>>()
            val processedWords =
                mutableSetOf<String>() // Kelimelerin işaretlenip işaretlenmediğini takip et

            // Kelime eşleşmelerini bul
            words.forEach { word ->
                val wordKey = word.key.orEmpty()
                var startIndex = text.indexOf(wordKey, ignoreCase = true)
                while (startIndex != -1) {
                    // Eğer kelime daha önce işaretlenmediyse, işaretle
                    if (wordKey !in processedWords) {
                        wordMatches.add(startIndex to word)
                        processedWords.add(wordKey) // Kelimeyi işaretlenmiş olarak kaydet
                    }
                    startIndex = text.indexOf(wordKey, startIndex + 1, ignoreCase = true)
                }
            }

            var lastIndex = 0
            val defaultStyle = SpanStyle(color = LocalAppColor.current.colorTextMain)

            // Metni işleyerek ekle
            wordMatches.sortedBy { it.first }.forEach { (startIndex, word) ->
                if (startIndex >= lastIndex) {
                    // Normal metni ekle
                    withStyle(style = defaultStyle) {
                        append(text.substring(lastIndex, startIndex))
                    }

                    // Kelimeyi ekle
                    pushStringAnnotation(tag = "WORD", annotation = "${word.key}|${word.value}")
                    withStyle(
                        style = SpanStyle(
                            color = if (selectedWord == word.key) {
                                LocalAppColor.current.colorTextMain
                            } else textColor,
                            textDecoration = if (selectedWord == word.key) {
                                TextDecoration.None
                            } else
                                TextDecoration.Underline,
                            background = if (selectedWord == word.key) {
                                textColor
                            } else {
                                Color.Transparent
                            }
                        )
                    ) {
                        append(word.key.orEmpty())
                    }
                    pop()

                    lastIndex = startIndex + word.key?.length!!
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
                val clickedWord = annotatedString.getStringAnnotations("WORD", offset, offset)
                    .firstOrNull()
                if (clickedWord != null) {
                    val (key, value) = clickedWord.item.split("|", limit = 2)
                    onWordClick(KeyValue(key, value))
                    selectedWord = key
                } else {
                    // Eğer kelime değilse, cümle bulun ve tetikleyin
                    sentences.find { sentence ->
                        offset in text.indexOf(sentence) until text.indexOf(sentence) + sentence.length
                    }?.let {
                        onSentenceClick(it)
                        selectedWord = it
                    }
                }
            }
        )
    }
}