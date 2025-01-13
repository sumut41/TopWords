package com.skyvo.mobile.core.uikit.compose.button

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.skyvo.mobile.core.uikit.compose.icon.AppIcon
import com.skyvo.mobile.core.uikit.compose.layout.AppSpacer
import com.skyvo.mobile.core.uikit.compose.text.AppText
import com.skyvo.mobile.core.uikit.theme.AppDimension
import com.skyvo.mobile.core.uikit.theme.AppPrimaryTheme
import com.skyvo.mobile.core.uikit.theme.AppTypography
import com.skyvo.mobile.core.uikit.theme.LocalAppColor

@Composable
fun AppPrimaryLargeButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    @DrawableRes icon: Int? = null,
    containerColor: Color = LocalAppColor.current.colorBackgroundButtonPrimaryDefault,
    contentColor: Color = LocalAppColor.current.colorTextOnPrimary,
    disabledContainerColor: Color = LocalAppColor.current.colorBackgroundButtonPrimaryDisabled,
    disabledContentColor: Color = LocalAppColor.current.colorTextOnPrimary.copy(alpha = 0.3f),
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
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            icon?.let {
                AppIcon(
                    imageVector = ImageVector.vectorResource(it),
                    tint = if (enabled) contentColor else disabledContentColor,
                    contentDescription = text
                )

                AppSpacer(
                    width = AppDimension.default.dp8
                )
            }
            AppText(
                text = text,
                style = AppTypography.default.bodyLarge,
                color = if (enabled) contentColor else disabledContentColor,
                maxLines = 1
            )
        }
    }
}

@Composable
fun AppPrimarySmallButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    containerColor: Color = LocalAppColor.current.colorBackgroundButtonPrimaryDefault,
    contentColor: Color = LocalAppColor.current.colorTextOnPrimary,
    disabledContainerColor: Color = LocalAppColor.current.colorBackgroundButtonPrimaryDisabled,
    disabledContentColor: Color = LocalAppColor.current.colorTextOnPrimary.copy(alpha = 0.3f),
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
fun PreviewButton() {
    AppPrimaryTheme {
        Column {
            AppPrimaryLargeButton(
                modifier = Modifier.padding(all = AppDimension.default.dp16),
                text = "Button Large"
            ) { }

            AppPrimaryLargeButton(
                modifier = Modifier.padding(all = AppDimension.default.dp16),
                enabled = false,
                text = "Button Disable"
            ) { }

            AppPrimarySmallButton(
                modifier = Modifier.padding(all = AppDimension.default.dp16),
                text = "Button Large"
            ) { }
        }
    }
}