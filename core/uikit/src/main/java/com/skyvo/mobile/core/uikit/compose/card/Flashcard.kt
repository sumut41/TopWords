package com.skyvo.mobile.core.uikit.compose.card

import android.annotation.SuppressLint
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.skyvo.mobile.core.uikit.compose.text.AppText
import com.skyvo.mobile.core.uikit.theme.AppDimension
import com.skyvo.mobile.core.uikit.theme.AppPrimaryTheme
import com.skyvo.mobile.core.uikit.theme.AppTypography
import com.skyvo.mobile.core.uikit.theme.LocalAppColor
import kotlinx.coroutines.delay

data class FlashcardItem(
    val word: String,
    val translate: String,
    val sentence: String,
    val sentenceTranslate: String,
    val level: String
)

@SuppressLint("NewApi")
@Composable
fun FlashcardStack(
    items: List<FlashcardItem>,
    isNavigateRight: Boolean,
    isNavigateLeft: Boolean,
    onSwipeRight: (FlashcardItem) -> Unit,
    onSwipeLeft: (FlashcardItem) -> Unit,
    onStackCompleted: () -> Unit
) {
    val cardStack = remember { mutableStateListOf(*items.toTypedArray()) }
    val currentCard = cardStack.firstOrNull()
    val offsetX = remember { Animatable(0f) }
    var isAnimating by remember { mutableStateOf(false) }
    var cardType by remember { mutableIntStateOf(0) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        contentAlignment = Alignment.Center
    ) {
        if (currentCard != null) {
            LaunchedEffect(isNavigateRight, isNavigateLeft) {
                if (!isAnimating) {
                    isAnimating = true
                    if (isNavigateRight) {
                        cardType = 1
                        delay(200)
                        offsetX.animateTo(500f, tween(500))
                        onSwipeRight(currentCard)
                        cardStack.removeAt(0)
                        if (cardStack.isEmpty()) {
                            onStackCompleted()
                        }
                        offsetX.snapTo(0f)
                        cardType = 0
                    } else if (isNavigateLeft) {
                        cardType = 2
                        delay(200)
                        offsetX.animateTo(-500f, tween(500))
                        onSwipeLeft(currentCard)
                        cardStack.removeAt(0)
                        if (cardStack.isEmpty()) {
                            onStackCompleted()
                        }
                        offsetX.snapTo(0f)
                        cardType = 0
                    }
                    isAnimating = false
                }
            }

            // Alttaki kartı göster (eğer varsa)
            if (cardStack.size > 1) {
                Flashcard(
                    item = cardStack[1],
                    modifier = Modifier.scale(0.95f),
                    cardType = 0,
                    backgroundColor = LocalAppColor.current.colorFlashCardBackground
                )
            }

            // Üstteki kartı göster
            Flashcard(
                item = currentCard,
                modifier = Modifier.offset(x = offsetX.value.dp),
                cardType = cardType,
                backgroundColor = when (cardType) {
                    0 -> LocalAppColor.current.colorFlashCardBackground
                    1 -> LocalAppColor.current.colorSuccess
                    2 -> LocalAppColor.current.colorError
                    else -> LocalAppColor.current.colorFlashCardBackground
                }
            )
        }
    }
}

@Composable
fun Flashcard(
    item: FlashcardItem,
    modifier: Modifier = Modifier,
    cardType: Int = 0,
    backgroundColor: Color = LocalAppColor.current.colorFlashCardBackground
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        modifier = modifier
            .fillMaxSize()
            .padding(
                top = 100.dp,
                start = AppDimension.default.dp30,
                end = AppDimension.default.dp30,
                bottom = 150.dp
            )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            AppText(
                text = item.level,
                style = AppTypography.default.body,
                color = LocalAppColor.current.colorTextMain,
                modifier = Modifier.padding(top = AppDimension.default.dp24)
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.weight(1f)
            ) {
                AppText(
                    text = item.word,
                    style = AppTypography.default.wordBody,
                    textAlign = TextAlign.Center
                )

                AppText(
                    text = item.translate,
                    style = AppTypography.default.bodyPrimary,
                    color = LocalAppColor.current.colorTextMain,
                    modifier = Modifier.padding(top = AppDimension.default.dp8)
                )

                val annotatedString = buildAnnotatedString {
                    val wordIndex = item.sentence.indexOf(" ${item.word}", ignoreCase = true) + 1
                    if (wordIndex >= 0) {
                        append(item.sentence.substring(0, wordIndex))

                        pushStyle(
                            androidx.compose.ui.text.SpanStyle(
                                color = if (cardType == 0) {
                                    LocalAppColor.current.colorError
                                } else {
                                    LocalAppColor.current.colorTextMain
                                },
                                textDecoration = TextDecoration.Underline
                            )
                        )
                        append(item.sentence.substring(wordIndex, wordIndex + item.word.length))
                        pop()
                        
                        // Kelimeden sonraki kısım
                        append(item.sentence.substring(wordIndex + item.word.length))
                    } else {
                        // Kelime cümlede yoksa tüm cümleyi normal şekilde göster
                        append(item.sentence)
                    }
                }

                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = AppDimension.default.dp24,
                            vertical = AppDimension.default.dp24
                        )
                        .background(
                            color = backgroundColor,
                            shape = RoundedCornerShape(AppDimension.default.dp10)
                        )
                        .border(
                            width = 1.dp,
                            color = if (cardType == 0) {
                                LocalAppColor.current.colorBorder
                            } else {
                                backgroundColor
                            },
                            shape = RoundedCornerShape(AppDimension.default.dp10)
                        )
                        .clip(RoundedCornerShape(AppDimension.default.dp10)),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = annotatedString,
                        style = AppTypography.default.bodyPrimary,
                        color = LocalAppColor.current.colorTextMain,
                        modifier = Modifier.padding(
                            top = AppDimension.default.dp16,
                            start = AppDimension.default.dp16,
                            end = AppDimension.default.dp16
                        ),
                        textAlign = TextAlign.Center
                    )

                    AppText(
                        text = item.sentenceTranslate,
                        style = AppTypography.default.body,
                        color = if (cardType == 0) {
                            LocalAppColor.current.colorTextSubtler
                        } else {
                            LocalAppColor.current.colorTextMain
                        },
                        modifier = Modifier.padding(
                            top = AppDimension.default.dp8,
                            start = AppDimension.default.dp16,
                            end = AppDimension.default.dp16,
                            bottom = AppDimension.default.dp16
                        ),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun FlashcardStackPreview() {
    var isNavigateRight by remember { mutableStateOf(false) }
    var isNavigateLeft by remember { mutableStateOf(false) }

    val items = listOf(
        FlashcardItem("mother", "[mʌðər]", "Örnek Cümle", "Örnek Cümle","A1 Level"),
        FlashcardItem("father", "[fɑːðər]", "Örnek Cümle", "Örnek Cümle", "A1 Level"),
        FlashcardItem("sister", "[sɪstər]", "Örnek Cümle", "Örnek Cümle", "A1 Level", )
    )

    AppPrimaryTheme {
        FlashcardStack(
            items = items,
            isNavigateRight = isNavigateRight,
            isNavigateLeft = isNavigateLeft,
            onSwipeRight = { isNavigateRight = false },
            onSwipeLeft = { isNavigateLeft = false },
            onStackCompleted = { /* Kartlar bittiğinde yapılacak işlem */ }
        )
    }
}