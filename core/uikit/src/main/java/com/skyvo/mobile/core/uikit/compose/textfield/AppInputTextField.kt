package com.skyvo.mobile.core.uikit.compose.textfield

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import com.skyvo.mobile.core.uikit.theme.AppDimension
import com.skyvo.mobile.core.uikit.theme.LocalAppColor

@Composable
fun AppInputTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String? = null,
    maxLength: Int = Int.MAX_VALUE,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    showError: Boolean = false,
    errorText: String? = null,
    labelText: String? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Text,
        imeAction = ImeAction.Done
    ),
    onImeAction: () -> Unit = {},
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    focusRequester: FocusRequester = remember { FocusRequester() },
    leftIcon: @Composable (() -> Unit)? = null,
    rightIcon: @Composable (() -> Unit)? = null,
    backgroundColor: Color = LocalAppColor.current.colorBackgroundInputsTextBox,
    shape: RoundedCornerShape = RoundedCornerShape(AppDimension.default.dp4),
    borderColor: Color = LocalAppColor.current.colorBorderInputsDefault,
    focusedBorderColor: Color = LocalAppColor.current.colorBorderInputsSelected,
    errorBorderColor: Color = LocalAppColor.current.colorTextInformationWarningBold,
    placeholderTextColor: Color = LocalAppColor.current.colorTextSubtle,
    onPreChangeValue: ((String) -> Unit)? = null
) {
    AppBasicTextField(
        modifier = modifier,
        value = value,
        onValueChange = {
            onValueChange.invoke(it)
        },
        placeholder = placeholder,
        maxLength = maxLength,
        enabled = enabled,
        readOnly = readOnly,
        singleLine = singleLine,
        maxLines = maxLines,
        showError = showError,
        errorText = errorText,
        labelText = labelText,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        onImeAction = onImeAction,
        interactionSource = interactionSource,
        focusRequester = focusRequester,
        leftIcon = leftIcon,
        rightIcon = rightIcon,
        backgroundColor = backgroundColor,
        shape = shape,
        borderColor = borderColor,
        focusedBorderColor = focusedBorderColor,
        errorBorderColor = errorBorderColor,
        placeholderTextColor = placeholderTextColor,
        onPreChangeValue = onPreChangeValue
    )
}