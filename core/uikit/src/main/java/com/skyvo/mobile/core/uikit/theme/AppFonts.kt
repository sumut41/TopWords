package com.skyvo.mobile.core.uikit.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.skyvo.mobile.core.uikit.R

class AppFonts {
    companion object {
        val Goldman = FontFamily(
            Font(R.font.goldman_regular, FontWeight.Normal),
        )

        val Inter = FontFamily(
            Font(R.font.inter_bold, FontWeight.Bold),
            Font(R.font.inter_semi_bold, FontWeight.SemiBold),
            Font(R.font.inter_regular, FontWeight.Normal),
            Font(R.font.inter_black, FontWeight.Black),
            Font(R.font.inter_medium, FontWeight.Medium),
            Font(R.font.inter_extra_bold, FontWeight.ExtraBold),
            Font(R.font.inter_light, FontWeight.Light),
        )
    }
}