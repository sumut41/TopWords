package com.skyvo.mobile.core.uikit.screen

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.skyvo.mobile.core.uikit.R
import com.skyvo.mobile.core.uikit.compose.button.AppPrimaryLargeButton
import com.skyvo.mobile.core.uikit.compose.button.AppSecondaryLargeButton
import com.skyvo.mobile.core.uikit.compose.layout.AppSpacer
import com.skyvo.mobile.core.uikit.compose.scaffold.AppScaffold
import com.skyvo.mobile.core.uikit.compose.text.AppText
import com.skyvo.mobile.core.uikit.theme.AppDimension
import com.skyvo.mobile.core.uikit.theme.AppTypography
import com.skyvo.mobile.core.uikit.theme.LocalAppColor

@Composable
fun AppFullScreenInformation(
    title: String,
    message: String,
    @DrawableRes icon: Int = R.drawable.ic_android_black_24dp, //R.drawable.warning todo
    iconSize: Dp = AppDimension.default.dp48,
    @DrawableRes contentImageRes: Int? = null,
    secondaryButtonText: String? = null,
    onSecondaryClick: (() -> Unit)? = null,
    primaryButtonText: String,
    onPrimaryClick: () -> Unit,
) {
    AppScaffold(
        backgroundColor = LocalAppColor.current.colorSurfaceBase,
        bottomView = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                if (onSecondaryClick != null) {
                    AppSecondaryLargeButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(end = AppDimension.default.dp8),
                        text = secondaryButtonText.orEmpty()
                    ) {
                        onSecondaryClick.invoke()
                    }
                }
                AppPrimaryLargeButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    text = primaryButtonText
                ) {
                    onPrimaryClick.invoke()
                }
            }
        }
    ) {
        Column {
            AppSpacer(height = AppDimension.default.dp32)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppDimension.default.dp16),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.size(iconSize),
                    painter = painterResource(icon),
                    contentDescription = "Info Icon",
                    contentScale = ContentScale.FillBounds
                )

                AppText(
                    modifier = Modifier.padding(
                        start = AppDimension.default.dp16
                    ),
                    text = title,
                    style = AppTypography.default.bodyXXlargeSemiBold
                )
            }

            AppText(
                modifier = Modifier.padding(
                    start = AppDimension.default.dp16,
                    end = AppDimension.default.dp16,
                    top = AppDimension.default.dp24,
                ),
                text = message,
                style = AppTypography.default.body
            )

            contentImageRes?.let {
                AppSpacer(height = AppDimension.default.dp24)

                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(it)
                        .build(),
                    contentDescription = title,
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = AppDimension.default.dp24),
                    contentScale = ContentScale.FillWidth
                )
            }
        }
    }
}