package com.skyvo.mobile.core.uikit.compose.picker

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.skyvo.mobile.core.uikit.R
import com.skyvo.mobile.core.uikit.compose.icon.AppIcon
import com.skyvo.mobile.core.uikit.theme.AppDimension
import com.skyvo.mobile.core.uikit.theme.LocalAppColor

@Composable
fun AppBasePickerComponent(
    modifier: Modifier = Modifier,
    backgroundColor: Color = LocalAppColor.current.colorSurfaceBase,
    borderColor: Color = LocalAppColor.current.colorBorderInputsDefault,
    contentPadding: PaddingValues = PaddingValues(all = AppDimension.default.dp16),
    isVisibleArrowRightIcon: Boolean = true,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    Row(modifier = modifier
        .clickable {
            if (onClick != null) {
                onClick()
            }
        }
        .clip(RoundedCornerShape(AppDimension.default.dp4))
        .border(
            width = AppDimension.default.dp1,
            color = borderColor,
            shape = RoundedCornerShape(AppDimension.default.dp4)
        )
        .background(backgroundColor), verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = Modifier
            .padding(contentPadding)
            .weight(1f)) {
            content()
        }
        if (isVisibleArrowRightIcon) {
            AppIcon(
                modifier = Modifier.padding(all = AppDimension.default.dp16),
                imageVector = ImageVector.vectorResource(R.drawable.ic_android_black_24dp), // arrow down
                contentDescription = "Selection Icon",
                tint = LocalAppColor.current.colorIcon
            )
        }
    }
}