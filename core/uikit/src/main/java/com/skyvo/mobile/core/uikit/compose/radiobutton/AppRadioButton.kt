package com.skyvo.mobile.core.uikit.compose.radiobutton

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.skyvo.mobile.core.uikit.compose.layout.AppSpacer
import com.skyvo.mobile.core.uikit.compose.text.AppText
import com.skyvo.mobile.core.uikit.theme.AppDimension
import com.skyvo.mobile.core.uikit.theme.AppTypography
import com.skyvo.mobile.core.uikit.theme.LocalAppColor
import com.skyvo.mobile.core.uikit.util.ghostClickable

@Composable
fun AppRadioButton(
    modifier: Modifier = Modifier,
    text: String? = null,
    isSelected: Boolean = false,
    enabled: Boolean = true,
    appRadioButtonDirection: AppRadioButtonDirection = AppRadioButtonDirection.LEFT,
    selectedColor: Color = LocalAppColor.current.colorRadioButtonChecked,
    unSelectedColor: Color = LocalAppColor.current.colorRadioButtonUnChecked,
    textColor: Color = LocalAppColor.current.colorTextSubtler,
    textStyle: TextStyle = AppTypography.default.body,
    onClick: (() -> Unit)?
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        if (appRadioButtonDirection == AppRadioButtonDirection.LEFT) {
            RadioButton(
                modifier = Modifier.size(AppDimension.default.dp24),
                selected = isSelected,
                enabled = enabled,
                onClick = { onClick?.invoke() },
                colors = RadioButtonDefaults.colors(
                    selectedColor = selectedColor,
                    unselectedColor = unSelectedColor
                )
            )
            AppSpacer(width = AppDimension.default.dp8)
        }
        if (text != null) {
            AppText(
                modifier = Modifier
                    .weight(1f)
                    .ghostClickable {
                        if (enabled) {
                            onClick?.invoke()
                        }
                    },
                text = text,
                color = textColor,
                style = textStyle
            )
        }
        if (appRadioButtonDirection == AppRadioButtonDirection.RIGHT) {
            AppSpacer(width = AppDimension.default.dp8)
            RadioButton(
                modifier = Modifier.size(AppDimension.default.dp24),
                selected = isSelected,
                enabled = enabled,
                onClick = { onClick?.invoke() },
                colors = RadioButtonDefaults.colors(
                    selectedColor = selectedColor,
                    unselectedColor = unSelectedColor
                )
            )
        }
    }
}