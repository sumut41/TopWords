package com.skyvo.mobile.core.uikit.compose.text

import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.skyvo.mobile.core.uikit.theme.AppFonts
import com.skyvo.mobile.core.uikit.theme.LocalAppColor

data class AppClickableTextArea(
    var text: String,
    val onClick: (data: String) -> Unit,
)

data class AppClickableTextData(
    val text:String? = null,
    val tag:String? = null,
    val onClick: ((data: AnnotatedString.Range<String>) -> Unit)? = null
)

@Composable
fun AppClickableText(
    modifier: Modifier = Modifier,
    text: String,
    clickableTextList: List<AppClickableTextArea> = emptyList(),
    spanColor: Color = LocalAppColor.current.colorTextSubtler,
    defaultColor: Color = LocalAppColor.current.colorTextSubtler
) {
    val appClickableTextData = mutableListOf<AppClickableTextData>()
    if (clickableTextList.isEmpty()) {
        appClickableTextData.add(AppClickableTextData(text))
    } else {
        var startIndex = 0
        clickableTextList.forEachIndexed { i, link ->
            var endIndex = text.indexOf(link.text)
            if (endIndex == -1) {
                endIndex = 0
                link.text = ""
            }
            appClickableTextData.add(AppClickableTextData(text.substring(startIndex, endIndex)))
            appClickableTextData.add(
                AppClickableTextData(
                    text = link.text,
                    tag = "${link.text}_TAG",
                    onClick = {
                        link.onClick.invoke(it.item)
                    }
                )
            )
            startIndex = endIndex + link.text.length
            if (i == clickableTextList.lastIndex && startIndex < text.length) {
                appClickableTextData.add(AppClickableTextData(text.substring(startIndex, text.length)))
            }
        }
    }

    val annotatedString = buildAnnotatedString {
        appClickableTextData.forEach { underlinedTextData ->
            if (underlinedTextData.tag != null && underlinedTextData.text != null) {
                pushStringAnnotation(
                    tag = underlinedTextData.tag,
                    annotation = underlinedTextData.text
                )
                withStyle(
                    style = SpanStyle(
                        color = spanColor,
                        textDecoration = TextDecoration.Underline,
                        fontFamily = AppFonts.Inter,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        letterSpacing = 0.2.sp
                    )
                ) {
                    append(underlinedTextData.text)
                }
                pop()
            } else {
                append(underlinedTextData.text)
            }
        }
    }

    ClickableText(
        text = annotatedString,
        style = TextStyle(
            color = defaultColor,
            fontFamily = AppFonts.Inter,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.2.sp
        ),
        onClick = { offset ->
            appClickableTextData.forEach { annotatedStringData ->
                if (annotatedStringData.tag != null && annotatedStringData.text != null) {
                    annotatedString.getStringAnnotations(
                        tag = annotatedStringData.tag,
                        start = offset,
                        end = offset
                    ).firstOrNull()?.let {
                        annotatedStringData.onClick?.invoke(it)
                    }
                }
            }
        }, modifier = modifier
    )
}