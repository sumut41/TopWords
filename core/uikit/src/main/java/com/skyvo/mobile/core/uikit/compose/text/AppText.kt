package com.skyvo.mobile.core.uikit.compose.text

import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.skyvo.mobile.core.uikit.theme.LocalAppColor

@Composable
fun AppText(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = LocalAppColor.current.colorTextMain,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    overflow: TextOverflow = TextOverflow.Ellipsis,
    style: TextStyle = LocalTextStyle.current
) {
    Text(
        modifier = modifier,
        text = text,
        color = color,
        textDecoration = textDecoration,
        textAlign = textAlign,
        maxLines = maxLines,
        minLines = minLines,
        onTextLayout = onTextLayout,
        overflow = overflow,
        style = style
    )
}

@Preview
@Composable
private fun AppTextPreview() {
    AppText(text = "SKYVOO")
}