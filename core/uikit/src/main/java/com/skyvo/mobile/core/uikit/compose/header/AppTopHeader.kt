package com.skyvo.mobile.core.uikit.compose.header

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalViewConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import com.skyvo.mobile.core.uikit.R
import com.skyvo.mobile.core.uikit.compose.icon.AppIcon
import com.skyvo.mobile.core.uikit.compose.text.AppText
import com.skyvo.mobile.core.uikit.theme.AppDimension
import com.skyvo.mobile.core.uikit.theme.AppTypography
import com.skyvo.mobile.core.uikit.theme.LocalAppColor
import com.skyvo.mobile.core.uikit.util.uppercaseTurkish

@Composable
fun AppTopHeader(
    title: String,
    onBackClickListener: (() -> Unit)? = null,
    withLogo: Boolean = false,
    isUpperCaseText: Boolean = true
) {
    Row (
        modifier = Modifier
            .background(LocalAppColor.current.colorSurfaceBase)
            .fillMaxWidth()
            .heightIn(min = AppDimension.default.dp56)
            .drawBehind {
                drawLine(
                    brush = Brush.linearGradient(
                        listOf(
                            // buraya colorHeaderBorderStart - End renklerini ekle topheaderın çizgisi
                        )
                    ),
                    start = Offset(0f, size.height),
                    end = Offset(x = size.width, y = size.height),
                    strokeWidth = AppDimension.default.dp1.toPx()
                )
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            modifier = Modifier.size(LocalViewConfiguration.current.minimumTouchTargetSize),
            onClick = {
                onBackClickListener?.invoke()
            }
        ) {
            onBackClickListener?.let {
                AppIcon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_android_black_24dp),
                    contentDescription = "Go Back ic_back"
                )
            }
        }

        AppText(
            text = if (isUpperCaseText) {
                title.uppercaseTurkish()
            } else {
                title
            },
            style = AppTypography.default.bodyBold,
            textAlign = TextAlign.Center
        )

        IconButton(
            modifier = Modifier.size(LocalViewConfiguration.current.minimumTouchTargetSize),
            onClick = {  }
        ) {
            if (withLogo) {
                Image(
                    painterResource(R.drawable.ic_android_black_24dp),
                    contentDescription = "logo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(AppDimension.default.dp30)
                        .width(AppDimension.default.dp22)
                )
            }
        }
    }
}

@Composable
fun AppTopHeader(
    title: String,
    onBackClickListener: () -> Unit,
    onCloseClickListener: () -> Unit,
    isUpperCaseText: Boolean = true
) {
    Row (
        modifier = Modifier
            .background(LocalAppColor.current.colorSurfaceBase)
            .fillMaxWidth()
            .heightIn(min = AppDimension.default.dp56)
            .drawBehind {
                drawLine(
                    brush = Brush.linearGradient(
                        listOf(
                            // buraya colorHeaderBorderStart - End renklerini ekle topheaderın çizgisi
                        )
                    ),
                    start = Offset(0f, size.height),
                    end = Offset(x = size.width, y = size.height),
                    strokeWidth = AppDimension.default.dp1.toPx()
                )
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            modifier = Modifier.size(LocalViewConfiguration.current.minimumTouchTargetSize),
            onClick = onBackClickListener

        ) {
            AppIcon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_android_black_24dp),
                contentDescription = "Go Back ic_back"
            )
        }

        AppText(
            text = if (isUpperCaseText) {
                title.uppercaseTurkish()
            } else {
                title
            },
            style = AppTypography.default.bodyBold,
            textAlign = TextAlign.Center
        )

        IconButton(
            modifier = Modifier.size(LocalViewConfiguration.current.minimumTouchTargetSize),
            onClick = onCloseClickListener

        ) {
            AppIcon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_android_black_24dp),
                contentDescription = "Close ic_close"
            )
        }
    }
}

@Composable
fun AppTopHeader(
    title: String,
    @DrawableRes rightIcon: Int,
    onBackClickListener: () -> Unit,
    onRightClickListener: () -> Unit,
) {
    Row (
        modifier = Modifier
            .background(LocalAppColor.current.colorSurfaceBase)
            .fillMaxWidth()
            .heightIn(min = AppDimension.default.dp56)
            .drawBehind {
                drawLine(
                    brush = Brush.linearGradient(
                        listOf(
                            // buraya colorHeaderBorderStart - End renklerini ekle topheaderın çizgisi
                        )
                    ),
                    start = Offset(0f, size.height),
                    end = Offset(x = size.width, y = size.height),
                    strokeWidth = AppDimension.default.dp1.toPx()
                )
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            modifier = Modifier.size(LocalViewConfiguration.current.minimumTouchTargetSize),
            onClick = onBackClickListener

        ) {
            AppIcon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_android_black_24dp),
                contentDescription = "Go Back ic_back"
            )
        }

        AppText(
            text = title.uppercaseTurkish(),
            style = AppTypography.default.bodyBold,
            textAlign = TextAlign.Center
        )

        IconButton(
            modifier = Modifier.size(LocalViewConfiguration.current.minimumTouchTargetSize),
            onClick = onRightClickListener

        ) {
            AppIcon(
                imageVector = ImageVector.vectorResource(rightIcon),
                contentDescription = "Logo"
            )
        }
    }
}