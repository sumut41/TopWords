package com.skyvo.mobile.core.uikit.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class AppCoreColorScheme(
    val background: Color = Color.Unspecified,
    val onBackground: Color = Color.Unspecified,
    val primary: Color = Color.Unspecified,
    val onPrimary: Color = Color.Unspecified,
    val secondary: Color = Color.Unspecified,
    val surface: Color = Color.Unspecified,
    val onSurface: Color = Color.Unspecified,
    val colorSurfaceBase: Color = Color.Unspecified,
    val colorSecondarySurface: Color = Color.Unspecified,
    val colorTextMain: Color = Color.Unspecified,
    val colorTextOnPrimary: Color = Color.Unspecified,
    val colorTextOnSecondary: Color = Color.Unspecified,
    val colorBackgroundButtonPrimaryDefault: Color = Color.Unspecified,
    val colorBackgroundButtonSecondaryDefault: Color = Color.Unspecified,
    val colorBackgroundButtonPrimaryDisabled: Color = Color.Unspecified,
    val colorBackgroundButtonSecondaryDisabled: Color = Color.Unspecified,
    val colorTextDisabled: Color = Color.Unspecified,
    val colorBorderInputsDefault: Color = Color.Unspecified,
    val colorBorderSelected: Color = Color.Unspecified,
    val colorBackgroundSelected: Color = Color.Unspecified,
    val colorCheckBoxChecked: Color = Color.Unspecified,
    val colorCheckBoxUnchecked: Color = Color.Unspecified,
    val colorTextSubtler: Color = Color.Unspecified,
    val colorIcon: Color = Color.Unspecified,
    val colorIconBackground: Color = Color.Unspecified,
    val colorBackgroundDecorativeGreySubtler: Color = Color.Unspecified,
    val colorBackgroundInputsToggleBaseOff: Color = Color.Unspecified,
    val colorRadioButtonChecked: Color = Color.Unspecified,
    val colorRadioButtonUnChecked: Color = Color.Unspecified,
    val colorTextInformationWarningBold: Color = Color.Unspecified,
    val colorBackgroundInputsTextBox: Color = Color.Unspecified,
    val colorBorderInputsSelected: Color = Color.Unspecified,
    val colorBackgroundInformationSubtle: Color = Color.Unspecified,
    val colorIconBrandBoldRed: Color = Color.Unspecified,
    val colorProgressBarForeground: Color = Color.Unspecified,
    val colorProgressBarBackground: Color = Color.Unspecified,
    val colorIndicatorDefault: Color = Color.Unspecified,
    val colorBottomMenu: Color = Color.Unspecified,
    val colorA1Level: Color = Color.Unspecified,
    val colorA2Level: Color = Color.Unspecified,
    val colorB1Level: Color = Color.Unspecified,
    val colorB2Level: Color = Color.Unspecified,
    val colorC1Level: Color = Color.Unspecified,
    val colorC2Level: Color = Color.Unspecified,
    val colorBooksLevel: Color = Color.Unspecified,
    val colorBottomSheetText: Color = Color.Unspecified,
    val colorTabBackgroundColor: Color = Color.Unspecified,
    val colorCircleProgressDefault: Color = Color.Unspecified,
    val colorBorder: Color = Color.Unspecified,
    val colorClickWord: Color = Color.Unspecified,
    val colorFlashCardBackground: Color = Color.Unspecified,
    val colorFlashCardLearnBackground: Color = Color.Unspecified,
    val colorSuccess: Color = Color.Unspecified,
    val colorError: Color = Color.Unspecified,
    val colorBorderFocused: Color = Color.Unspecified,
    val colorAnswerSuccess: Color = Color.Unspecified,
    val colorAnswerError: Color = Color.Unspecified,
    val colorBeginner: Color = Color.Unspecified,
    val colorIntermediate: Color = Color.Unspecified,
    val colorAdvanced: Color = Color.Unspecified,
    val colorTextLevelCard: Color = Color.Unspecified,
    val colorHighLightRed: Color = Color.Unspecified,
)

val AppLightColors = AppCoreColorScheme(
    background = Color(0xFFFFFFFE),
    onBackground = Color(0xFF14161B),
    primary = Color(0xFFFFC222),
    secondary = Color(0xFF42226E),
    onPrimary = Color(0xFFFFC222),
    surface = Color(0xFFFFFFFE),
    onSurface = Color(0xFF14161B),
    colorSecondarySurface = Color(0xFFFAFAFA),
    colorSurfaceBase = Color(0xFFFFFFFE),
    colorTextMain = Color(0xFF08162C),
    colorTextOnPrimary = Color(0xFF14161B),
    colorTextOnSecondary = Color(0xFF8D8DA6),
    colorBackgroundButtonPrimaryDefault = Color(0xFFFFC222),
    colorBackgroundButtonSecondaryDefault = Color(0xFFFFFFFE),
    colorBackgroundButtonPrimaryDisabled = Color(0x59FFB81C),
    colorBackgroundButtonSecondaryDisabled = Color(0xFFFFFFFE),
    colorTextDisabled = Color(0xFFFFFFFF),
    colorBorderInputsDefault = Color(0xFFE8E8E8),
    colorBorderSelected = Color(0xFFFFC222),
    colorBackgroundSelected = Color(0xFFF8F8F7),
    colorCheckBoxChecked = Color(0xFFFFC222),
    colorCheckBoxUnchecked = Color(0xFFDADACE),
    colorTextSubtler = Color(0xFF8D8DA6),
    colorIcon = Color(0xFF262E3D),
    colorIconBackground = Color(0xFFD8DBE4),
    colorBackgroundDecorativeGreySubtler = Color(0xFF919191),
    colorBackgroundInputsToggleBaseOff = Color(0x9E021C5D),
    colorRadioButtonChecked = Color(0xFFFFC222),
    colorRadioButtonUnChecked = Color(0xFFF1F1DB),
    colorTextInformationWarningBold = Color(0xFFE13B51),
    colorBackgroundInputsTextBox = Color(0xFFFFFFFE),
    colorBorderInputsSelected = Color(0x37092B49),
    colorBackgroundInformationSubtle = Color(0x48AFA9A9),
    colorIconBrandBoldRed = Color(0xD39D031B),
    colorProgressBarForeground = Color(0xFFFFC222),
    colorProgressBarBackground = Color(0x48AFA9A9),
    colorIndicatorDefault = Color(0xFFD8DBE4),
    colorBottomMenu = Color(0xFFFFFFFE),
    colorA1Level = Color(0xFF0EA489),
    colorA2Level = Color(0xFF62A40E),
    colorB1Level = Color(0xFFA48C0E),
    colorB2Level = Color(0xFFA44D0E),
    colorC1Level = Color(0xFFDC401D),
    colorC2Level = Color(0xFFDC1D62),
    colorBooksLevel = Color(0xFFFFFFFE),
    colorBottomSheetText = Color(0x0FFFFFFF),
    colorTabBackgroundColor = Color(0xFFF8F8F8),
    colorCircleProgressDefault = Color(0xFFFFEBD3),
    colorBorder = Color(0xFFE8E8E8),
    colorClickWord = Color(0xFF8D8DA6),
    colorSuccess = Color(0xFF21B628),
    colorError = Color(0xFFD73245),
    colorFlashCardBackground = Color(0xFFFFFFFE),
    colorFlashCardLearnBackground = Color(0xFFECECEC),
    colorBorderFocused = Color(0xFFE8E8E8),
    colorAnswerSuccess = Color(0xFFF5FFD8),
    colorAnswerError = Color(0xFFFFDDD8),
    colorBeginner = Color(0xFFE6F5F3),
    colorIntermediate = Color(0xFFE6F7FE),
    colorAdvanced = Color(0xFFFAF0ED),
    colorTextLevelCard = Color(0xFF31363F),
    colorHighLightRed = Color(0xFFD73245),
)

val AppDarkColors = AppCoreColorScheme(
    background = Color(0xFF14161B),
    onBackground = Color(0xFFFCFCFD),
    primary = Color(0xFFF1CC06),
    secondary = Color(0xFF7950C3),
    onPrimary = Color(0xFFF1CC06),
    surface = Color(0xFF14161B),
    onSurface = Color(0xFFFFFFFE),
    colorSecondarySurface = Color(0xFF14161B),
    colorSurfaceBase = Color(0xFF14161B),
    colorTextMain = Color(0xFFFFFFFE),
    colorTextOnPrimary = Color(0xFF14161B),
    colorTextOnSecondary = Color(0xFF83899F),
    colorBackgroundButtonPrimaryDefault = Color(0xFFF1CC06),
    colorBackgroundButtonSecondaryDefault = Color(0xFF313843),
    colorBackgroundButtonPrimaryDisabled = Color(0x59F1CC06),
    colorBackgroundButtonSecondaryDisabled = Color(0x4D313843),
    colorTextDisabled = Color(0xFFFFFFFF),
    colorBorderInputsDefault = Color(0xFF282D36),
    colorBorderSelected = Color(0xFFF1CC06),
    colorBackgroundSelected = Color(0xFF1C1F26),
    colorCheckBoxChecked = Color(0xFFFFEB3B),
    colorCheckBoxUnchecked = Color(0xFFEAEAE2),
    colorTextSubtler = Color(0xFF83899F),
    colorIcon = Color(0xFFFFFFFE),
    colorIconBackground = Color(0xFF22252E),
    colorBackgroundDecorativeGreySubtler = Color(0xFFC2BFBF),
    colorBackgroundInputsToggleBaseOff = Color(0xFFC3D2EF),
    colorRadioButtonChecked = Color(0xFFF1CC06),
    colorRadioButtonUnChecked = Color(0xFFF1F1DB),
    colorTextInformationWarningBold = Color(0xFFDE6F7E),
    colorBackgroundInputsTextBox = Color(0xFFF3ECED),
    colorBorderInputsSelected = Color(0xFFF3ECED),
    colorBackgroundInformationSubtle = Color(0xFFF1F1F1),
    colorIconBrandBoldRed = Color(0xD39D031B),
    colorProgressBarForeground = Color(0xFFFFEB3B),
    colorProgressBarBackground = Color(0x48AFA9A9),
    colorIndicatorDefault = Color(0xFF474850),
    colorBottomMenu = Color(0xFF1C1F26),
    colorA1Level = Color(0xFF0EA489),
    colorA2Level = Color(0xFF62A40E),
    colorB1Level = Color(0xFFA48C0E),
    colorB2Level = Color(0xFFA44D0E),
    colorC1Level = Color(0xFFDC401D),
    colorC2Level = Color(0xFFDC1D62),
    colorBooksLevel = Color(0xFFFFFFFE),
    colorBottomSheetText = Color(0xFFFFFFFF),
    colorTabBackgroundColor = Color(0xFF1C1F26),
    colorCircleProgressDefault = Color(0xFF282D36),
    colorBorder = Color(0xFF282D36),
    colorClickWord = Color(0xFF5B5B5B),
    colorSuccess = Color(0xFF108C15),
    colorError = Color(0xFFFF5858),
    colorFlashCardBackground = Color(0xFF171A1F),
    colorFlashCardLearnBackground = Color(0xFF121418),
    colorBorderFocused = Color(0xFF282D36),
    colorAnswerSuccess = Color(0xFFC9F8BC),
    colorAnswerError = Color(0xFFFDCDC6),
    colorBeginner = Color(0xFFE3316F),
    colorIntermediate = Color(0xFF197BEF),
    colorAdvanced = Color(0xFF965FFD),
    colorTextLevelCard = Color(0xFFFFFFFE),
    colorHighLightRed = Color(0xFF810919),
)

val LocalAppColor = staticCompositionLocalOf { AppCoreColorScheme() }
