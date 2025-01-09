package com.skyvo.mobile.core.uikit.compose.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.skyvo.mobile.core.uikit.theme.AppDimension
import com.skyvo.mobile.core.uikit.theme.AppTypography
import com.skyvo.mobile.core.uikit.theme.LocalAppColor
import com.skyvo.mobile.core.uikit.compose.layout.AppSpacer
import com.skyvo.mobile.core.uikit.compose.text.AppText

@Composable
fun AppOtpTextField(
    modifier: Modifier = Modifier,
    otpText: String,
    otpCount: Int = 6,
    showError: Boolean = false,
    errorText: String = "",
    onOtpTextChange: (String, Boolean) -> Unit
) {

    val configuration = LocalConfiguration.current
    val width = (configuration.screenWidthDp.dp - AppDimension.default.dp32) - (otpCount * 8).dp
    val cellSize = (width / otpCount.dp).dp

    BasicTextField(
        modifier = modifier,
        value = TextFieldValue(otpText, selection = TextRange(otpText.length)),
        onValueChange = {
            if (it.text.length <= otpCount) {
                onOtpTextChange.invoke(it.text, it.text.length == otpCount)
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
        decorationBox = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Row {
                    repeat(otpCount) { index ->
                        AppOtpItemView(
                            index = index,
                            text = otpText,
                            size = cellSize,
                            isErrorOtp = showError
                        )
                        AppSpacer(width = AppDimension.default.dp8)
                    }
                }
                if (showError) {
                    AppSpacer(height = AppDimension.default.dp4)
                    AppText(
                        text = errorText,
                        style = AppTypography.default.body,
                        color = LocalAppColor.current.colorTextInformationWarningBold
                    )
                }
            }
        }
    )
}

@Composable
private fun AppOtpItemView(
    index: Int,
    text: String,
    size: Dp,
    isErrorOtp: Boolean = false
) {
    val char = when {
        index == text.length -> "-"
        index > text.length -> "_"
        else -> text[index].toString()
    }

    Row(modifier = Modifier
        .width(size)
        .height(AppDimension.default.dp52)
        .background(
            color = LocalAppColor.current.colorBackgroundInputsTextBox,
            RoundedCornerShape(AppDimension.default.dp4)
        )
        .border(
            AppDimension.default.dp1,
            if (isErrorOtp) LocalAppColor.current.colorTextInformationWarningBold
            else LocalAppColor.current.colorBorderInputsDefault,
            RoundedCornerShape(AppDimension.default.dp4)
            )
        .padding(vertical = AppDimension.default.dp16, horizontal = AppDimension.default.dp16),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        AppText(
            text = char,
            style = AppTypography.default.bodyBold,
            textAlign = TextAlign.Center
        )
    }
}