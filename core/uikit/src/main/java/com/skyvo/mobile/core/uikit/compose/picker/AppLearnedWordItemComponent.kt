package com.skyvo.mobile.core.uikit.compose.picker

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.skyvo.mobile.core.uikit.R
import com.skyvo.mobile.core.uikit.compose.icon.AppIcon
import com.skyvo.mobile.core.uikit.compose.text.AppText
import com.skyvo.mobile.core.uikit.theme.AppDimension
import com.skyvo.mobile.core.uikit.theme.AppPrimaryTheme
import com.skyvo.mobile.core.uikit.theme.AppTypography
import com.skyvo.mobile.core.uikit.theme.LocalAppColor

@Composable
fun AppLearnedWordItemComponent(
    modifier: Modifier = Modifier,
    backgroundColor: Color = LocalAppColor.current.colorBackgroundSelected,
    startContent: (@Composable () -> Unit)? = null,
    word: String,
    translatedWord: String,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(AppDimension.default.dp16)
            )
            .border(
                width = 1.dp,
                color = LocalAppColor.current.primary,
                shape = RoundedCornerShape(AppDimension.default.dp16)
            )
            .clip(RoundedCornerShape(AppDimension.default.dp16)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row (
            modifier = Modifier
                .padding(start = AppDimension.default.dp16),
            verticalAlignment = Alignment.CenterVertically
        ) {
            startContent?.let {
                startContent()
            }
            Column(
                modifier = Modifier.padding(
                    start = AppDimension.default.dp12
                ),
            ) {
                AppText(
                    modifier = Modifier,
                    text = word,
                    style = AppTypography.default.bodyPrimary
                )

                AppText(
                    modifier = Modifier,
                    text = translatedWord,
                    style = AppTypography.default.body,
                    color = LocalAppColor.current.colorTextSubtler
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppLearnedWordItemComponentPreview() {
    AppPrimaryTheme {
        AppLearnedWordItemComponent(
            startContent = {
                AppIcon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_night),
                    contentDescription = ""
                )
            },
            word = "sa",
            translatedWord = "sub"
        )
    }
}