package com.skyvo.mobile.core.uikit.compose.header

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.skyvo.mobile.core.uikit.R
import com.skyvo.mobile.core.uikit.compose.icon.AppIcon
import com.skyvo.mobile.core.uikit.compose.text.AppText
import com.skyvo.mobile.core.uikit.theme.AppDimension
import com.skyvo.mobile.core.uikit.theme.AppPrimaryTheme
import com.skyvo.mobile.core.uikit.theme.AppTypography
import com.skyvo.mobile.core.uikit.theme.LocalAppColor

@Composable
fun AppTopLongHeader(
    title: String,
    backgroundColor: Color = LocalAppColor.current.colorSurfaceBase,
    onBackClickListener: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .height(AppDimension.default.longHeaderHeight)
            .background(color = backgroundColor),
        verticalAlignment = Alignment.Top
    ) {
        onBackClickListener?.let {
            Box(
                modifier = Modifier
                    .padding(
                        top = AppDimension.default.dp24,
                        start = AppDimension.default.dp24
                    )
                    .size(AppDimension.default.dp32)
                    .background(
                        color = LocalAppColor.current.colorIconBackground,
                        shape = RoundedCornerShape(AppDimension.default.dp10)
                    )
                    .clickable {
                        onBackClickListener.invoke()
                    },
                contentAlignment = Alignment.Center
            ) {
                AppIcon(
                    modifier = Modifier.size(AppDimension.default.dp20),
                    imageVector = ImageVector.vectorResource(R.drawable.ic_back),
                    tint = LocalAppColor.current.colorIcon,
                    contentDescription = "Theme"
                )
            }
        }

        AppText(
            modifier = Modifier
                .padding(top = AppDimension.default.dp20)
                .fillMaxWidth()
                .padding(
                    horizontal = AppDimension.default.dp16
                ),
            text = title,
            style = AppTypography.default.headerBold,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun PreviewAppTopLongHeader() {
    AppPrimaryTheme {
        AppTopLongHeader(
            title = "What language are you want to study?"
        ) {
            // click
        }
    }
}