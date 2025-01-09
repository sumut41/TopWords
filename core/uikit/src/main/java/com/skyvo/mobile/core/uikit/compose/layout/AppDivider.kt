package com.skyvo.mobile.core.uikit.compose.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.skyvo.mobile.core.uikit.theme.AppDimension
import com.skyvo.mobile.core.uikit.theme.LocalAppColor

@Composable
fun AppDivider(
    modifier: Modifier = Modifier,
    height: Dp = AppDimension.default.dp1,
    color: Color = LocalAppColor.current.colorBorderInputsDefault
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .background(color = color)
    ) {  }
}