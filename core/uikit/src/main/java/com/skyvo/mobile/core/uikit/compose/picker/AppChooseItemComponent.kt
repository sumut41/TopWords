package com.skyvo.mobile.core.uikit.compose.picker

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.skyvo.mobile.core.uikit.compose.checkbox.AppCheckBox
import com.skyvo.mobile.core.uikit.compose.text.AppText
import com.skyvo.mobile.core.uikit.theme.AppDimension
import com.skyvo.mobile.core.uikit.theme.AppPrimaryTheme
import com.skyvo.mobile.core.uikit.theme.AppTypography
import com.skyvo.mobile.core.uikit.theme.LocalAppColor

@Composable
fun AppChooseItemComponent(
    modifier: Modifier = Modifier,
    backgroundColor: Color = LocalAppColor.current.colorBackgroundSelected,
    startContent: (@Composable () -> Unit)? = null,
    text: String,
    isSelected: Boolean,
    onSelectListener: (isSelect: Boolean) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(AppDimension.default.dp16)
            )
            .border(
                width = 1.dp,
                color = if (isSelected) LocalAppColor.current.primary else LocalAppColor.current.colorBackgroundSelected,
                shape = RoundedCornerShape(AppDimension.default.dp16)
            )
            .clip(RoundedCornerShape(AppDimension.default.dp16))
            .clickable {
                onSelectListener.invoke(isSelected.not())
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .padding(start = AppDimension.default.dp16)
                .weight(9f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            startContent?.let {
                startContent()
            }

            AppText(
                modifier = Modifier
                    .padding(start = AppDimension.default.dp12),
                text = text,
                style = AppTypography.default.bodyPrimary
            )
        }

        AppCheckBox(
            modifier = Modifier
                .padding(end = AppDimension.default.dp16)
                .weight(1f),
            isChecked = isSelected
        ) {
            onSelectListener.invoke(isSelected.not())
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
fun AppChooseItemComponentPreview() {
    AppPrimaryTheme {
        AppChooseItemComponent(
            startContent = {},
            text = "sa",
            isSelected = true,
            onSelectListener = {}
        )
    }
}