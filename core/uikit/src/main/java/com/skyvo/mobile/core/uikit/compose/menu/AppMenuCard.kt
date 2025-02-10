package com.skyvo.mobile.core.uikit.compose.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.skyvo.mobile.core.uikit.R
import com.skyvo.mobile.core.uikit.compose.icon.AppIcon
import com.skyvo.mobile.core.uikit.compose.text.AppText
import com.skyvo.mobile.core.uikit.theme.AppDimension
import com.skyvo.mobile.core.uikit.theme.AppTypography
import com.skyvo.mobile.core.uikit.theme.LocalAppColor

@Composable
fun AppMenuCard(
    modifier: Modifier = Modifier,
    title: String? = null,
    subTitle: String? = null,
    color: Color = LocalAppColor.current.colorA1Level,
    menuIcon: Int = R.drawable.ic_settings,
    horizontalPaddingValues: Dp = AppDimension.default.dp16,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = horizontalPaddingValues,
                vertical = AppDimension.default.dp4
            )
            .background(
                color = LocalAppColor.current.colorTabBackgroundColor,
                shape = RoundedCornerShape(AppDimension.default.dp10)
            )
            .clip(
                RoundedCornerShape(AppDimension.default.dp10)
            )
            .clickable {
                onClick.invoke()
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .padding(start = AppDimension.default.dp12)
                .size(
                    width = 36.dp,
                    height = 32.dp
                )
                .background(
                    color = color,
                    shape = RoundedCornerShape(10.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            AppIcon(
                modifier = Modifier.size(AppDimension.default.dp24),
                imageVector = ImageVector.vectorResource(menuIcon),
                tint = Color.White,
                contentDescription = title.orEmpty()
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .weight(8f)
            ) {
                AppText(
                    modifier = Modifier
                        .padding(
                            top = AppDimension.default.dp12,
                            start = AppDimension.default.dp16
                        ),
                    text = title.orEmpty(),
                    style = AppTypography.default.body
                )
                AppText(
                    modifier = Modifier
                        .padding(
                            start = AppDimension.default.dp16,
                            bottom = AppDimension.default.dp12
                        ),
                    text = subTitle.orEmpty(),
                    style = AppTypography.default.bodySmall,
                    color = LocalAppColor.current.colorTextSubtler
                )
            }

            AppIcon(
                modifier = Modifier
                    .weight(1f)
                    .size(AppDimension.default.dp24)
                    .padding(end = AppDimension.default.dp16)
                    .alpha(0.5f),
                imageVector = ImageVector.vectorResource(R.drawable.ic_box_arrow_right),
                tint = LocalAppColor.current.colorTextSubtler,
                contentDescription = title.orEmpty()
            )
        }
    }
}