package com.skyvo.mobile.core.uikit.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.skyvo.mobile.core.uikit.R
import com.skyvo.mobile.core.uikit.theme.LocalAppColor

@Composable
fun GetLevelColor(type: String): Color {
    return when (type) {
        "A1" -> LocalAppColor.current.colorA1Level
        "A2" -> LocalAppColor.current.colorA2Level
        "B1" -> LocalAppColor.current.colorB1Level
        "B2" -> LocalAppColor.current.colorB2Level
        "C1" -> LocalAppColor.current.colorC1Level
        "C2" -> LocalAppColor.current.colorC2Level
        else -> LocalAppColor.current.primary
    }
}

@Composable
fun GetLevelIcon(type: String): Int {
    return when (type) {
        "A1","A2" -> R.drawable.ic_level_a1
        "B1","B2" -> R.drawable.ic_level_b1
        else -> R.drawable.ic_level_c2
    }
}