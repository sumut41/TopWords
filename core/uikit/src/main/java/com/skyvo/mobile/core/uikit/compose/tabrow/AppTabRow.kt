package com.skyvo.mobile.core.uikit.compose.tabrow

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.nativeCanvas
import com.skyvo.mobile.core.uikit.compose.text.AppText
import com.skyvo.mobile.core.uikit.theme.AppDimension
import com.skyvo.mobile.core.uikit.theme.LocalAppColor

@Composable
fun AppTabRow(
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    items: List<String>,
    onSelectionChange: (Int) -> Unit
) {
    BoxWithConstraints(
        modifier
            .padding(AppDimension.default.dp8)
            .height(AppDimension.default.dp56)
            .clip(RoundedCornerShape(AppDimension.default.dp8))
            .background(LocalAppColor.current.primary)
            .padding(AppDimension.default.dp8)
    ) {
        if (items.isNotEmpty()) {
            val maxWidth = this.maxWidth
            val tabWidth = maxWidth / items.size

            val indicatorOffset by animateDpAsState(
                targetValue = tabWidth * selectedIndex,
                animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing),
                label = "indicator offset"
            )

            // Box shadow and background
            Box(
                modifier = Modifier
                    .offset(x = indicatorOffset)
                    .shadow(AppDimension.default.dp2, RoundedCornerShape(AppDimension.default.dp8))
                    .width(tabWidth)
                    .fillMaxHeight()
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .drawWithContent {
                        val padding = AppDimension.default.dp4.toPx()
                        val itemWidth = size.width / items.size

                        drawRoundRect(
                            topLeft = Offset(x = indicatorOffset.toPx() + padding, y = padding),
                            size = Size(itemWidth - padding * 2, size.height - padding * 2),
                            color = Color.Black,
                            cornerRadius = CornerRadius(
                                x = AppDimension.default.dp8.toPx(),
                                y = AppDimension.default.dp8.toPx()
                            )
                        )

                        drawWithLayer {
                            drawContent()

                            drawRoundRect(
                                topLeft = Offset(x = indicatorOffset.toPx(), y = 0f),
                                size = Size(itemWidth, size.height),
                                color = Color.White,
                                cornerRadius = CornerRadius(
                                    x = AppDimension.default.dp8.toPx(),
                                    y = AppDimension.default.dp8.toPx()
                                ),
                                blendMode = BlendMode.SrcOut
                            )
                        }
                    }
            ) {
                items.forEachIndexed { index, text ->
                    Box(
                        modifier = Modifier
                            .width(tabWidth)
                            .fillMaxHeight()
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                                onClick = { onSelectionChange(index) }
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        AppText(
                            text = text,
                            color = LocalAppColor.current.colorTextMain
                        )
                    }
                }
            }
        }
    }
}

fun ContentDrawScope.drawWithLayer(block: ContentDrawScope.() -> Unit) {
    with(drawContext.canvas.nativeCanvas) {
        val checkPoint = saveLayer(null, null)
        block()
        restoreToCount(checkPoint)
    }
}