package com.skyvo.mobile.core.uikit.compose.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import com.skyvo.mobile.core.uikit.theme.AppDimension
import com.skyvo.mobile.core.uikit.theme.AppTypography
import com.skyvo.mobile.core.uikit.theme.LocalAppColor
import com.skyvo.mobile.core.uikit.R
import com.skyvo.mobile.core.uikit.compose.icon.AppIcon
import com.skyvo.mobile.core.uikit.compose.text.AppText

@Composable
fun AppSearchTextField(
    modifier: Modifier,
    value: String,
    onSearch: (String) -> Unit,
    placeholder: String? = null,
    enabled: Boolean = true,
    height: Dp = AppDimension.default.dp52,
    backgroundColor: Color = LocalAppColor.current.colorBackgroundInformationSubtle,
    shape: RoundedCornerShape = RoundedCornerShape(AppDimension.default.dp4),
    borderColor: Color = LocalAppColor.current.colorBorderInputsDefault,
    focusedBorderColor: Color = LocalAppColor.current.colorBorderInputsSelected,
    onImeAction: () -> Unit = {},
) {
    var isFocused by remember { mutableStateOf(false) }

    BasicTextField(
        modifier = modifier
            .focusRequester(FocusRequester())
            .onFocusChanged {
                isFocused = it.isFocused
            },
        value = value,
        onValueChange = onSearch,
        textStyle = AppTypography.default.body.copy(
            color = LocalAppColor.current.colorTextMain
        ),
        maxLines = 1,
        singleLine = true,
        enabled = enabled,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done,
            autoCorrectEnabled = false
        ),
        keyboardActions = KeyboardActions(onDone = { onImeAction() }),
        decorationBox = @Composable { innerTextField ->
            Row(
                modifier = Modifier
                    .height(height)
                    .background(
                        color = backgroundColor,
                        shape = shape
                    )
                    .border(
                        width = AppDimension.default.dp1,
                        color = if (isFocused) {
                            focusedBorderColor
                        } else {
                            borderColor
                        },
                        shape = shape
                    )
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .layoutId("searchIcon"),
                    contentAlignment = Alignment.Center
                ) {
                    IconButton(
                        modifier = Modifier
                            .padding(
                                start = AppDimension.default.dp16,
                                end = AppDimension.default.dp8
                            )
                            .size(AppDimension.default.dp24),
                        onClick = {}
                    ) {
                        AppIcon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_android_black_24dp),
                            contentDescription = "search",
                            tint = LocalAppColor.current.colorIcon
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = AppDimension.default.dp16)
                ) {
                    if (value.isEmpty() && placeholder != null) {
                        AppText(
                            text = placeholder,
                            style = AppTypography.default.body,
                            color = LocalAppColor.current.colorTextSubtle
                        )
                    }
                    innerTextField()
                }
            }
        }
    )
}