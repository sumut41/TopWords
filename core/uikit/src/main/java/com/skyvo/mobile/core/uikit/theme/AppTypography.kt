package com.skyvo.mobile.core.uikit.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp

@Immutable
data class AppTypography(
    val logoTextStyle: TextStyle,
    val bigLargeTitleBold: TextStyle,
    val brandMedium: TextStyle,
    val headerBold: TextStyle,
    val brandTitle: TextStyle,
    val bodyXXlargeSemiBold: TextStyle,
    val bodyExtraLarge: TextStyle,
    val bodyExtraLargeBold: TextStyle,
    val bodyExtraLargeSemiBold: TextStyle,
    val bodyPrimary: TextStyle,
    val bodyLarge: TextStyle,
    val bodyBold: TextStyle,
    val body: TextStyle,
    val bodyUnderline: TextStyle,
    val bodyBoldUnderline: TextStyle,
    val bodySmallBold: TextStyle,
    val bodySmall: TextStyle,
    val bodyTinyBold: TextStyle,
    val bodyTiny: TextStyle,
    val caption: TextStyle,
    val subTitleBold: TextStyle
) {
    companion object {
        val default = AppTypography(
            logoTextStyle = TextStyle(
                fontFamily = AppFonts.Goldman,
                fontWeight = FontWeight.Normal,
                fontSize = 30.sp,
                lineHeight = 36.sp,
                letterSpacing = 0.2.sp,
            ),
            bigLargeTitleBold = TextStyle(
                fontFamily = AppFonts.Inter,
                fontWeight = FontWeight.Black,
                fontSize = 36.sp,
                lineHeight = 40.sp,
                letterSpacing = 0.2.sp,
            ),
            brandMedium = TextStyle(
                fontFamily = AppFonts.Inter,
                fontWeight = FontWeight.Medium,
                fontSize = 32.sp,
                lineHeight = 28.sp,
                letterSpacing = 0.2.sp,
            ),
            brandTitle = TextStyle(
                fontFamily = AppFonts.Inter,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                lineHeight = 28.sp,
                letterSpacing = 0.2.sp,
            ),
            headerBold = TextStyle(
                fontFamily = AppFonts.Inter,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                lineHeight = 26.sp,
                letterSpacing = 0.2.sp,
            ),
            bodyXXlargeSemiBold = TextStyle(
                fontFamily = AppFonts.Inter,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                lineHeight = 24.sp,
                letterSpacing = 0.2.sp
            ),
            bodyExtraLargeBold = TextStyle(
                fontFamily = AppFonts.Inter,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                lineHeight = 24.sp,
                letterSpacing = 0.2.sp
            ),
            bodyExtraLargeSemiBold = TextStyle(
                fontFamily = AppFonts.Inter,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                lineHeight = 24.sp,
                letterSpacing = 0.2.sp
            ),
            bodyExtraLarge = TextStyle(
                fontFamily = AppFonts.Inter,
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp,
                lineHeight = 28.sp,
                letterSpacing = 0.4.sp
            ),
            bodyLarge = TextStyle(
                fontFamily = AppFonts.Inter,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                lineHeight = 18.sp,
                letterSpacing = 0.2.sp
            ),
            bodyBold = TextStyle(
                fontFamily = AppFonts.Inter,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                lineHeight = 16.sp,
                letterSpacing = 0.2.sp
            ),
            body = TextStyle(
                fontFamily = AppFonts.Inter,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                lineHeight = 16.sp,
                letterSpacing = 0.2.sp
            ),
            bodyPrimary = TextStyle(
                fontFamily = AppFonts.Inter,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                lineHeight = 18.sp,
                letterSpacing = 0.2.sp
            ),
            bodyUnderline = TextStyle(
                fontFamily = AppFonts.Inter,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                lineHeight = 16.sp,
                letterSpacing = 0.2.sp,
                textDecoration = TextDecoration.Underline
            ),
            bodyBoldUnderline = TextStyle(
                fontFamily = AppFonts.Inter,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                lineHeight = 16.sp,
                letterSpacing = 0.2.sp,
                textDecoration = TextDecoration.Underline
            ),
            bodySmallBold = TextStyle(
                fontFamily = AppFonts.Inter,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                lineHeight = 14.sp,
                letterSpacing = 0.2.sp
            ),
            bodySmall = TextStyle(
                fontFamily = AppFonts.Inter,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                lineHeight = 14.sp,
                letterSpacing = 0.2.sp
            ),
            bodyTinyBold = TextStyle(
                fontFamily = AppFonts.Inter,
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp,
                lineHeight = 12.sp,
                letterSpacing = 0.2.sp
            ),
            bodyTiny = TextStyle(
                fontFamily = AppFonts.Inter,
                fontWeight = FontWeight.Normal,
                fontSize = 10.sp,
                lineHeight = 12.sp,
                letterSpacing = 0.2.sp
            ),
            caption = TextStyle(
                fontFamily = AppFonts.Inter,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                lineHeight = 16.sp,
                letterSpacing = 0.2.sp
            ),
            subTitleBold = TextStyle(
                fontFamily = AppFonts.Inter,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                lineHeight = 16.sp,
                letterSpacing = 0.2.sp
            )
        )
    }
}