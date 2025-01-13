package com.skyvo.mobile.core.uikit.compose.indicator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.skyvo.mobile.core.uikit.compose.layout.AppSpacer
import com.skyvo.mobile.core.uikit.theme.AppDimension
import com.skyvo.mobile.core.uikit.theme.LocalAppColor

@Composable
fun AppPageIndicator(
    modifier: Modifier = Modifier,
    currentPosition: Int,
    pageCount: Int,
    indicatorActiveSize: Dp = AppDimension.default.dp16,
    indicatorInActiveSize: Dp = AppDimension.default.dp8,
    activeColor: Color = LocalAppColor.current.primary,
    inActiveColor: Color = LocalAppColor.current.colorIndicatorDefault
) {
    Row(
        modifier = modifier
    ) {
        repeat(pageCount) { index ->
            Box(
                modifier = Modifier
                    .width(if (index == currentPosition) indicatorActiveSize else indicatorInActiveSize)
                    .height(AppDimension.default.dp8)
                    .background(
                        color = if (index == currentPosition) activeColor else inActiveColor,
                        shape = RoundedCornerShape(AppDimension.default.dp8)
                    )
            )
            AppSpacer(width = AppDimension.default.dp5)
        }
    }
}