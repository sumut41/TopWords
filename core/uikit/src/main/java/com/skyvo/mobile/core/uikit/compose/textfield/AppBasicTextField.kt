package com.skyvo.mobile.core.uikit.compose.textfield

import android.view.KeyEvent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import com.skyvo.mobile.core.uikit.theme.AppDimension
import com.skyvo.mobile.core.uikit.theme.AppTypography
import com.skyvo.mobile.core.uikit.theme.LocalAppColor
import com.skyvo.mobile.core.uikit.util.notContainsClearCharacters

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AppBasicTextField(
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
    isEmojiSupport: Boolean = false,
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
    val focusManager = LocalFocusManager.current
    val isFocused = interactionSource.collectIsFocusedAsState().value

    BasicTextField(
        value = value,
        onValueChange = { text ->
            if (onPreChangeValue == null) {
                if (text.length <= maxLength) {
                    if (isEmojiSupport) {
                        onValueChange(
                            if (singleLine) {
                                text.replace("[\n\r]".toRegex(), replacement = "")
                            } else {
                                text
                            }
                        )
                    } else {
                        if (text.notContainsClearCharacters().not()) {
                            onValueChange(
                                if (singleLine) {
                                    text.replace("[\n\r]".toRegex(), replacement = "")
                                } else {
                                    text
                                }
                            )
                        }
                    }
                }
            }
        },
        modifier = modifier
            .focusRequester(focusRequester)
            .onPreviewKeyEvent {
                if (singleLine && it.key == Key.Enter && it.nativeKeyEvent.action == KeyEvent.ACTION_DOWN) {
                    onImeAction()
                    true
                } else if (it.key == Key.Tab && it.nativeKeyEvent.action == KeyEvent.ACTION_DOWN) {
                    focusManager.moveFocus(FocusDirection.Down)
                    true
                } else {
                    false
                }
            },
        enabled = enabled,
        readOnly = readOnly,
        textStyle = AppTypography.default.body.copy(
            color = LocalAppColor.current.colorTextMain
        ),
        singleLine = singleLine,
        maxLines = maxLines,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions.copy(autoCorrectEnabled = false),
        interactionSource = interactionSource,
        cursorBrush = SolidColor(LocalAppColor.current.colorTextMain),
        decorationBox = { innerTextField ->
            Column {
                Row(
                    modifier = Modifier
                        .heightIn(min = AppDimension.default.dp52)
                        .background(
                            color = backgroundColor,
                            shape = shape
                        )
                        .border(
                            width = AppDimension.default.dp1,
                            color = if (isFocused) {
                                if (showError) {
                                    errorBorderColor
                                } else {
                                    focusedBorderColor
                                }
                            } else {
                                if (showError) {
                                    errorBorderColor
                                } else {
                                    borderColor
                                }
                            },
                            shape = shape
                        )
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (leftIcon != null) {
                        Box(
                            modifier = Modifier
                                .layoutId("leftIcon")
                                .padding(start = AppDimension.default.dp16),
                            contentAlignment = Alignment.Center
                        ) {
                            leftIcon()
                        }
                    }

                    Box(
                        modifier = Modifier.weight(1f)
                    ) {
                        TextFieldDefaults.TextFieldDecorationBox(
                            value = value,
                            innerTextField = innerTextField,
                            enabled = enabled,
                            singleLine = singleLine,
                            visualTransformation = visualTransformation,
                            placeholder = {
                                if (!isFocused && !placeholder.isNullOrEmpty()) {
                                    Text(
                                        text = placeholder,
                                        style = AppTypography.default.body,
                                        color = placeholderTextColor
                                    )
                                }
                            },
                            label = {
                                if ((isFocused || value.isNotEmpty()) && !labelText.isNullOrEmpty()) {
                                    Text(
                                        text = labelText,
                                        maxLines = if (singleLine) 1 else maxLines,
                                        style = AppTypography.default.bodySmallBold,
                                        color = LocalAppColor.current.colorTextSubtle
                                    )
                                } else {
                                    if (!placeholder.isNullOrEmpty()) {
                                        Text(
                                            text = placeholder,
                                            maxLines = if (singleLine) 1 else maxLines,
                                            style = AppTypography.default.body,
                                            color = LocalAppColor.current.colorTextSubtle
                                        )
                                    }
                                }
                            },
                            leadingIcon = null,
                            trailingIcon = null,
                            isError = false,
                            interactionSource = interactionSource,
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = LocalAppColor.current.colorTextMain,
                                backgroundColor = backgroundColor,
                                cursorColor = LocalAppColor.current.colorTextMain,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                focusedLabelColor = LocalAppColor.current.colorTextSubtle,
                                placeholderColor = LocalAppColor.current.colorTextSubtle
                            )
                        )
                    }

                    if (rightIcon != null) {
                        Box(
                            modifier = Modifier
                                .layoutId("rightIcon")
                                .padding(end = AppDimension.default.dp16),
                            contentAlignment = Alignment.Center
                        ) {
                            rightIcon()
                        }
                    }
                }
                AnimatedVisibility(visible = showError && errorText != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = AppDimension.default.dp1,
                                top = AppDimension.default.dp4
                            )
                    ) {
                        Text(
                            text = errorText.orEmpty(),
                            modifier = Modifier.fillMaxWidth(),
                            style = AppTypography.default.bodySmall.copy(
                                color = LocalAppColor.current.colorTextInformationWarningBold
                            )
                        )
                    }
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AppBasicTextField(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
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
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
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
    val focusManager = LocalFocusManager.current
    val isFocused = interactionSource.collectIsFocusedAsState().value

    BasicTextField(
        value = value,
        onValueChange = { text ->
            if (onPreChangeValue == null) {
                if (text.text.length <= maxLength) {
                    onValueChange(
                        if (singleLine) {
                            text.copy(text = text.text.replace("[\n\r]".toRegex(), replacement = ""))
                        } else {
                            text
                        }
                    )
                }
            }
        },
        modifier = modifier
            .focusRequester(focusRequester)
            .onPreviewKeyEvent {
                if (singleLine && it.key == Key.Enter && it.nativeKeyEvent.action == KeyEvent.ACTION_DOWN) {
                    onImeAction()
                    focusManager.clearFocus()
                    true
                } else if (it.key == Key.Tab && it.nativeKeyEvent.action == KeyEvent.ACTION_DOWN) {
                    focusManager.moveFocus(FocusDirection.Down)
                    true
                } else {
                    false
                }
            },
        enabled = enabled,
        readOnly = readOnly,
        textStyle = AppTypography.default.body.copy(
            color = LocalAppColor.current.colorTextMain
        ),
        singleLine = singleLine,
        maxLines = maxLines,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        interactionSource = interactionSource,
        cursorBrush = SolidColor(LocalAppColor.current.colorTextMain),
        decorationBox = { innerTextField ->
            Column {
                Row(
                    modifier = Modifier
                        .heightIn(min = AppDimension.default.dp52)
                        .background(
                            color = backgroundColor,
                            shape = shape
                        )
                        .border(
                            width = AppDimension.default.dp1,
                            color = if (isFocused) {
                                if (showError) {
                                    errorBorderColor
                                } else {
                                    focusedBorderColor
                                }
                            } else {
                                if (showError) {
                                    errorBorderColor
                                } else {
                                    borderColor
                                }
                            },
                            shape = shape
                        )
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (leftIcon != null) {
                        Box(
                            modifier = Modifier
                                .layoutId("leftIcon"),
                            contentAlignment = Alignment.Center
                        ) {
                            leftIcon()
                        }
                    }

                    Box(
                        modifier = Modifier.weight(1f)
                    ) {
                        TextFieldDefaults.TextFieldDecorationBox(
                            value = value.text,
                            innerTextField = innerTextField,
                            enabled = enabled,
                            singleLine = singleLine,
                            visualTransformation = visualTransformation,
                            placeholder = {
                                if (!isFocused) {
                                    Text(
                                        text = placeholder.orEmpty(),
                                        style = AppTypography.default.body,
                                        color = placeholderTextColor
                                    )
                                }
                            },
                            label = {
                                if (!isFocused || value.text.isNotEmpty()) {
                                    Text(
                                        text = labelText.orEmpty(),
                                        maxLines = if (singleLine) 1 else maxLines,
                                        style = AppTypography.default.bodySmallBold,
                                        color = LocalAppColor.current.colorTextSubtle
                                    )
                                } else {
                                    Text(
                                        text = placeholder.orEmpty(),
                                        maxLines = if (singleLine) 1 else maxLines,
                                        style = AppTypography.default.body,
                                        color = LocalAppColor.current.colorTextSubtle
                                    )
                                }
                            },
                            leadingIcon = null,
                            trailingIcon = null,
                            isError = false,
                            interactionSource = interactionSource,
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = LocalAppColor.current.colorTextMain,
                                backgroundColor = backgroundColor,
                                cursorColor = LocalAppColor.current.colorTextMain,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                focusedLabelColor = LocalAppColor.current.colorTextSubtle,
                                placeholderColor = LocalAppColor.current.colorTextSubtle
                            )
                        )
                    }

                    if (rightIcon != null) {
                        Box(
                            modifier = Modifier
                                .layoutId("rightIcon"),
                            contentAlignment = Alignment.Center
                        ) {
                            rightIcon()
                        }
                    }
                }

                AnimatedVisibility(visible = showError && errorText != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = AppDimension.default.dp1,
                                top = AppDimension.default.dp4
                            )
                    ) {
                        Text(
                            text = errorText.orEmpty(),
                            modifier = Modifier.fillMaxWidth(),
                            style = AppTypography.default.bodySmall.copy(
                                color = LocalAppColor.current.colorTextInformationWarningBold
                            )
                        )
                    }
                }
            }
        }
    )
}