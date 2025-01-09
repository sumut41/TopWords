package com.skyvo.mobile.core.uikit.compose.text

import androidx.compose.foundation.clickable
import androidx.compose.material3 .Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.skyvo.mobile.core.uikit.theme.AppFonts
import com.skyvo.mobile.core.uikit.theme.LocalAppColor

@Composable
fun AppUnderlinedText(
    modifier: Modifier = Modifier,
    text: String? = null,
    color: Color = LocalAppColor.current.colorTextMain,
    textAlign: TextAlign? = null,
    onClick: (() -> Unit)? = null
) {
    text?.let {
        Text(
            modifier = modifier.clickable {
                if (onClick != null) {
                    onClick()
                }
            },
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        textDecoration = TextDecoration.Underline,
                        fontFamily = AppFonts.Inter,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        letterSpacing = 0.2.sp,
                        color = color
                    )
                ) {
                    append(it)
                }
            },
            textAlign = textAlign
        )
    }
}