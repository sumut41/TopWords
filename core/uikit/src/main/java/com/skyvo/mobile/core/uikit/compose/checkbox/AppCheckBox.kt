package com.skyvo.mobile.core.uikit.compose.checkbox

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.skyvo.mobile.core.uikit.compose.layout.AppSpacer
import com.skyvo.mobile.core.uikit.compose.text.AppClickableText
import com.skyvo.mobile.core.uikit.compose.text.AppClickableTextArea
import com.skyvo.mobile.core.uikit.compose.text.AppText
import com.skyvo.mobile.core.uikit.theme.AppDimension
import com.skyvo.mobile.core.uikit.theme.AppTypography
import com.skyvo.mobile.core.uikit.theme.LocalAppColor

@Composable
fun AppCheckBox(
    modifier: Modifier = Modifier,
    title: String? = null,
    titleTextStyle: TextStyle = AppTypography.default.bodyBold,
    description: String? = null,
    isChecked: Boolean,
    checkedColor: Color = LocalAppColor.current.colorCheckBoxChecked,
    uncheckedColor: Color = LocalAppColor.current.colorCheckBoxUnchecked,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            modifier = Modifier.size(AppDimension.default.dp24),
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                checkedColor = checkedColor,
                uncheckedColor = uncheckedColor,
                checkmarkColor = LocalAppColor.current.background
            )
        )

        AppSpacer(width = AppDimension.default.dp4)

        Column {
            if (!title.isNullOrEmpty()) {
                AppText(
                    text = title,
                    style = titleTextStyle,
                    modifier = Modifier.padding(bottom = AppDimension.default.dp4)
                )
            }

            if (!description.isNullOrEmpty()) {
                AppText(
                    text = description,
                    style = AppTypography.default.body
                )
            }
        }
    }
}

@Composable
fun AppClickableCheckBox(
    modifier: Modifier = Modifier,
    description: String? = null,
    clickableText: List<AppClickableTextArea>? = null,
    isChecked: Boolean,
    checkedColor: Color = LocalAppColor.current.colorCheckBoxChecked,
    uncheckedColor: Color = LocalAppColor.current.colorCheckBoxUnchecked,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                checkedColor = checkedColor,
                uncheckedColor = uncheckedColor,
                checkmarkColor = LocalAppColor.current.background
            )
        )
        AppSpacer(width = AppDimension.default.dp4)
        if (!description.isNullOrEmpty()) {
            AppClickableText(
                text = description,
                clickableTextList = clickableText ?: emptyList()
            )
        }
    }
}