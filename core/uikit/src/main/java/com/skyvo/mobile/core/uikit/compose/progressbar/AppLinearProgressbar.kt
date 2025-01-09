package com.skyvo.mobile.core.uikit.compose.progressbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.skyvo.mobile.core.uikit.theme.AppDimension
import com.skyvo.mobile.core.uikit.theme.LocalAppColor

@Composable
fun AppLinearProgressbar(
    modifier: Modifier = Modifier,
    maxStep: Float,
    currentStep: Float,
    color: Color = LocalAppColor.current.colorProgressBarForeground,
    trackColor: Color = LocalAppColor.current.colorProgressBarBackground,
) {
    val configuration = LocalConfiguration.current
    val size = ((configuration.screenWidthDp.dp - AppDimension.default.dp56) / maxStep)
    Box(
        modifier = modifier
            .height(AppDimension.default.dp12)
            .padding(horizontal = AppDimension.default.dp12)
            .fillMaxWidth()
            .background(
                color = trackColor,
                shape = RoundedCornerShape(AppDimension.default.dp4)
            )
    ) {
        Box(modifier = Modifier
            .height(AppDimension.default.dp12)
            .width(size * currentStep)
            .background(color = color, shape = RoundedCornerShape(AppDimension.default.dp4))
        )
    }
}