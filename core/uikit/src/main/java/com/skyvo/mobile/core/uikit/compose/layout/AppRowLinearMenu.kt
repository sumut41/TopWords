package com.skyvo.mobile.core.uikit.compose.layout

import androidx.compose.foundation.background
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
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalViewConfiguration
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import com.skyvo.mobile.core.uikit.R
import com.skyvo.mobile.core.uikit.compose.icon.AppIcon
import com.skyvo.mobile.core.uikit.compose.text.AppText
import com.skyvo.mobile.core.uikit.theme.AppDimension
import com.skyvo.mobile.core.uikit.theme.AppTypography
import com.skyvo.mobile.core.uikit.theme.LocalAppColor

@Composable
fun AppRowLinearMenu(
    modifier: Modifier = Modifier,
    title: String,
    isNewTagVisible: Boolean = false,
    textColor: Color = LocalAppColor.current.colorSurfaceBase,
    background: Color = LocalAppColor.current.colorTextSubtler,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(background)
            .clickable {
                onClick.invoke()
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(AppDimension.default.dp48),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row {
                AppText(
                    modifier = Modifier.padding(horizontal = AppDimension.default.dp16),
                    text = title,
                    style = AppTypography.default.body,
                    textAlign = TextAlign.Center,
                    color = textColor
                )

                if (isNewTagVisible) {
                    Box(
                        modifier = Modifier
                            .background(
                                color = LocalAppColor.current.colorTextInformationWarningBold,
                                shape = RoundedCornerShape(AppDimension.default.dp12)
                            )
                    ) {
                        AppText(
                            modifier = Modifier
                                .padding(AppDimension.default.dp4),
                            text = "Yeni",
                            style = AppTypography.default.bodyTinyBold,
                            color = LocalAppColor.current.colorTextMain
                        )
                    }
                }
            }

            IconButton(
                modifier = Modifier.size(LocalViewConfiguration.current.minimumTouchTargetSize),
                onClick = {}
            ) {
                AppIcon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_android_black_24dp), // ic_arrow_right
                    contentDescription = title,
                    tint = LocalAppColor.current.colorIcon
                )
            }
        }

        AppDivider(
            color = LocalAppColor.current.colorIconBrandBoldRed
        )
    }
}