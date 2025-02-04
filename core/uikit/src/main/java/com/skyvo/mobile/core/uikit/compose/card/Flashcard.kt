package com.skyvo.mobile.core.uikit.compose.card

import android.annotation.SuppressLint
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.skyvo.mobile.core.uikit.R
import com.skyvo.mobile.core.uikit.compose.icon.AppIcon
import com.skyvo.mobile.core.uikit.compose.text.AppText
import com.skyvo.mobile.core.uikit.theme.AppDimension
import com.skyvo.mobile.core.uikit.theme.AppPrimaryTheme
import com.skyvo.mobile.core.uikit.theme.AppTypography
import com.skyvo.mobile.core.uikit.theme.LocalAppColor
import kotlinx.coroutines.delay

data class FlashcardItem(
    val word: String,
    val pronunciation: String,
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
                        delay(200) // Renk değişimini görmek için kısa bir bekleme
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
                        delay(200) // Renk değişimini görmek için kısa bir bekleme
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
                    backgroundColor = LocalAppColor.current.colorFlashCardBackground
                )
            }

            // Üstteki kartı göster
            Flashcard(
                item = currentCard,
                modifier = Modifier.offset(x = offsetX.value.dp),
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
                    text = item.pronunciation,
                    style = AppTypography.default.bodyPrimary,
                    color = LocalAppColor.current.colorTextMain,
                    modifier = Modifier.padding(top = AppDimension.default.dp8)
                )

                AppIcon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_words),
                    tint = LocalAppColor.current.colorIcon,
                    modifier = Modifier
                        .padding(top = AppDimension.default.dp32)
                        .size(AppDimension.default.dp24),
                    contentDescription = ""
                )
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
        FlashcardItem("mother", "[mʌðər]", "A1 Level"),
        FlashcardItem("father", "[fɑːðər]", "A1 Level"),
        FlashcardItem("sister", "[sɪstər]", "A1 Level")
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