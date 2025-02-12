package com.skyvo.mobile.core.uikit.compose.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.skyvo.mobile.core.uikit.compose.text.AppText
import com.skyvo.mobile.core.uikit.theme.AppDimension
import com.skyvo.mobile.core.uikit.theme.AppPrimaryTheme
import com.skyvo.mobile.core.uikit.theme.AppTypography
import com.skyvo.mobile.core.uikit.theme.LocalAppColor

@Composable
fun AppSecondaryLargeButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    containerColor: Color = LocalAppColor.current.colorBackgroundButtonSecondaryDefault,
    contentColor: Color = LocalAppColor.current.colorTextOnSecondary,
    disabledContainerColor: Color = LocalAppColor.current.colorBackgroundButtonSecondaryDisabled,
    disabledContentColor: Color = LocalAppColor.current.colorTextOnSecondary.copy(alpha = 0.3f),
    borderStrokeColor: Color = LocalAppColor.current.colorBorderInputsDefault,
    onClick: () -> Unit
) {
    AppButton(
        onClick = onClick,
        shape = RoundedCornerShape(AppDimension.default.buttonLargeRadius),
        modifier = modifier
            .fillMaxWidth()
            .height(AppDimension.default.buttonLargeHeight),
        enabled = enabled,
        contentPadding = PaddingValues(
            horizontal = AppDimension.default.dp12,
            vertical = AppDimension.default.dp8
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContentColor = disabledContentColor,
            disabledContainerColor = disabledContainerColor
        ),
        border = BorderStroke(
            width = AppDimension.default.dp1,
            color = if (enabled) borderStrokeColor else borderStrokeColor.copy(alpha = 0.3f)
        )
    ) {
        AppText(
            text = text,
            style = AppTypography.default.bodyLarge,
            color = if (enabled) contentColor else disabledContentColor,
            maxLines = 1
        )
    }
}

@Composable
fun AppSecondarySmallButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    containerColor: Color = LocalAppColor.current.colorBackgroundButtonSecondaryDefault,
    contentColor: Color = LocalAppColor.current.colorTextOnSecondary,
    disabledContainerColor: Color = LocalAppColor.current.colorBackgroundButtonSecondaryDisabled,
    disabledContentColor: Color = LocalAppColor.current.colorTextOnSecondary.copy(alpha = 0.3f),
    borderStrokeColor: Color = LocalAppColor.current.colorBorderInputsDefault,
    onClick: () -> Unit
) {
    AppButton(
        onClick = onClick,
        shape = RoundedCornerShape(AppDimension.default.buttonSmallRadius),
        modifier = modifier
            .height(AppDimension.default.buttonSmallHeight),
        enabled = enabled,
        contentPadding = PaddingValues(
            horizontal = AppDimension.default.dp12,
            vertical = AppDimension.default.dp8
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContentColor = disabledContentColor,
            disabledContainerColor = disabledContainerColor
        ),
        border = BorderStroke(
            width = AppDimension.default.dp1,
            color = if (enabled) borderStrokeColor else borderStrokeColor.copy(alpha = 0.3f)
        )
    ) {
        AppText(
            text = text,
            style = AppTypography.default.bodySmallBold,
            color = if (enabled) contentColor else disabledContentColor,
            maxLines = 1
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSecondaryButton() {
    AppPrimaryTheme {
        Column {
            AppSecondaryLargeButton (
                modifier = Modifier.padding(all = AppDimension.default.dp16),
                text = "Button Large"
            ) { }

            AppSecondaryLargeButton(
                modifier = Modifier.padding(all = AppDimension.default.dp16),
                enabled = false,
                text = "Button Disable"
            ) { }

            AppSecondarySmallButton(
                modifier = Modifier.padding(all = AppDimension.default.dp16),
                text = "Button Large"
            ) { }
        }
    }
}