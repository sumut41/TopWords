package com.skyvo.mobile.core.uikit.compose.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.skyvo.mobile.core.uikit.theme.AppDimension
import com.skyvo.mobile.core.uikit.theme.LocalAppColor

@Composable
fun AppVerticalDivider(
    modifier: Modifier = Modifier,
    border: Dp = AppDimension.default.dp1,
    height: Dp = AppDimension.default.dp16,
    color: Color = LocalAppColor.current.colorBorderInputsDefault
) {
    Box(
        modifier = modifier
            .width(border)
            .height(height)
            .background(color = color)
    ) { }
}