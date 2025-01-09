package com.skyvo.mobile.core.uikit.compose.header

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
fun AppTopHeader(
    title: String,
    backgroundColor: Color = LocalAppColor.current.colorSurfaceBase,
    onBackClickListener: (() -> Unit)? = null
) {
    Row (
        modifier = Modifier
            .height(AppDimension.default.headerHeight)
            .background(color = backgroundColor),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box {
            onBackClickListener?.let {
                IconButton(
                    modifier = Modifier
                        .padding(start = AppDimension.default.dp24)
                        .size(AppDimension.default.dp32)
                        .background(
                            color = LocalAppColor.current.colorIconBackground,
                            shape = RoundedCornerShape(AppDimension.default.dp10)
                        ),
                    onClick = {
                        onBackClickListener.invoke()
                    }
                ) {
                    AppIcon(
                        modifier = Modifier.size(AppDimension.default.dp30),
                        imageVector = ImageVector.vectorResource(R.drawable.ic_back),
                        tint = LocalAppColor.current.colorIcon,
                        contentDescription = "Go Back"
                    )
                }
            }

            AppText(
                modifier = Modifier.fillMaxWidth(),
                text = title,
                style = AppTypography.default.headerBold,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun PreviewHeader() {
    AppPrimaryTheme {
        AppTopHeader(
            title = "Dessadsa"
        ) {
            // click
        }
    }
}