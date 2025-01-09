package com.skyvo.mobile.core.uikit.compose.textfield

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.skyvo.mobile.core.uikit.theme.AppDimension
import com.skyvo.mobile.core.uikit.theme.LocalAppColor
import com.skyvo.mobile.core.uikit.R
import com.skyvo.mobile.core.uikit.compose.icon.AppIcon

@Composable
fun AppPasswordTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String? = null,
    maxLength: Int = Int.MAX_VALUE,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    showError: Boolean = false,
    errorText: String? = null,
    labelText: String? = null,
    onImeAction: () -> Unit = {},
    showEyeIcon: Boolean = true,
    imeAction: ImeAction = ImeAction.Done,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    focusRequester: FocusRequester = remember { FocusRequester() }
) {
    var showPassword by remember { mutableStateOf(false) }

    val pattern = remember { Regex("$|^[0-9]*\$") }

    AppInputTextField(
        modifier = modifier,
        value = value,
        onValueChange = {
            if (it.matches(pattern)) {
                onValueChange(it)
            }
        },
        placeholder = placeholder,
        maxLength = maxLength,
        enabled = enabled,
        readOnly = readOnly,
        singleLine = true,
        maxLines = 1,
        showError = showError,
        errorText = errorText,
        labelText = labelText,
        visualTransformation = if (showPassword) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.NumberPassword,
            imeAction = imeAction,
            capitalization = KeyboardCapitalization.None
        ),
        onImeAction = onImeAction,
        interactionSource = interactionSource,
        focusRequester = focusRequester,
        rightIcon = {
            if (showEyeIcon) {
                val icon = if (showPassword) {
                    // R.drawable.ic_eye_open
                } else {
                    // R.drawable.ic_eye_close
                }

                IconButton(
                    modifier = Modifier
                        .padding(end = AppDimension.default.dp16)
                        .size(AppDimension.default.dp20),
                    onClick = {
                        showPassword = showPassword.not()
                    }
                ) {
                    AppIcon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_android_black_24dp),//ImageVector.vectorResource(icon),
                        tint = LocalAppColor.current.colorIcon,
                        contentDescription = if (showPassword) {
                            "Şifreyi gizle"
                        } else {
                            "Şifreyi göster"
                        }
                    )
                }
            }
        }
    )
}