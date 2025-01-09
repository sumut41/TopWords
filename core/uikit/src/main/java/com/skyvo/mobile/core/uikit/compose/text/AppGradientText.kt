package com.skyvo.mobile.core.uikit.compose.text

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import com.skyvo.mobile.core.uikit.theme.LocalAppColor

@Composable
fun AppGradientText(
    modifier: Modifier = Modifier,
    text: String,
    style: TextStyle,
    colors: List<Color> = listOf(
        LocalAppColor.current.primary,
        LocalAppColor.current.colorBackgroundButtonPrimaryDefault
    ),
    textAlign: TextAlign? = null
) {
    AppText(
        modifier = modifier,
        text = text,
        style = style.copy(
            brush = Brush.linearGradient(
                colors = colors,
                tileMode = TileMode.Mirror
            )
        ),
        textAlign = textAlign
    )
}