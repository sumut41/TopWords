package com.skyvo.mobile.core.uikit.compose.picker

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.skyvo.mobile.core.uikit.compose.layout.AppSpacer
import com.skyvo.mobile.core.uikit.compose.text.AppText
import com.skyvo.mobile.core.uikit.theme.AppDimension
import com.skyvo.mobile.core.uikit.theme.AppPrimaryTheme
import com.skyvo.mobile.core.uikit.theme.AppTypography
import com.skyvo.mobile.core.uikit.theme.LocalAppColor
import com.skyvo.mobile.core.uikit.util.ghostClickable

@Composable
fun AppLearnedWordItemComponent(
    modifier: Modifier = Modifier,
    backgroundColor: Color = LocalAppColor.current.colorBackgroundSelected,
    word: String,
    translatedWord: String,
    isFavorite: Boolean = false,
    onSpeakClick: (String) -> Unit,
    onFavoriteClick: ((String) -> Unit)? = null,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(AppDimension.default.dp10)
            )
            .border(
                width = 1.dp,
                color = LocalAppColor.current.colorBorder,
                shape = RoundedCornerShape(AppDimension.default.dp10)
            )
            .clip(RoundedCornerShape(AppDimension.default.dp10)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AppSpacer(
            width = AppDimension.default.dp16
        )
        AppIcon(
            modifier = Modifier
                .weight(1f)
                .size(AppDimension.default.dp24)
                .ghostClickable {
                    onSpeakClick.invoke(word)
                },
            imageVector = ImageVector.vectorResource(R.drawable.ic_voice),
            tint = LocalAppColor.current.primary,
            contentDescription = "Pronounce word"
        )
        Column(
            modifier = Modifier
                .weight(10f)
                .padding(
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

        onFavoriteClick?.let {
            AppIcon(
                modifier = Modifier
                    .weight(1f)
                    .size(AppDimension.default.dp24)
                    .clickable {
                        onFavoriteClick(word)
                    },
                imageVector = ImageVector.vectorResource(
                    if (isFavorite) R.drawable.ic_favorite_filled else R.drawable.ic_favorite
                ),
                tint = if (isFavorite) {
                    LocalAppColor.current.colorError
                } else {
                    LocalAppColor.current.colorTextSubtler.copy(alpha = 0.7f)
                },
                contentDescription = "favorite"
            )
        } ?: run {
            Box (
                modifier = Modifier
                    .weight(1f)
                    .size(AppDimension.default.dp24)
            ) {
            }
        }
        AppSpacer(
            width = AppDimension.default.dp16
        )
    }
}