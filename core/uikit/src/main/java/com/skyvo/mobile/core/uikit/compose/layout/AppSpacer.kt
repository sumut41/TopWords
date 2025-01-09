package com.skyvo.mobile.core.uikit.compose.layout

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
fun AppSpacer(
    modifier: Modifier = Modifier,
    height: Dp? = null,
    width: Dp? = null
) {
    height?.let {
        Spacer(modifier = modifier.height(it))
    }
    width?.let {
        Spacer(modifier = modifier.width(it))
    }
}