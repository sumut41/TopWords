package com.skyvo.mobile.core.uikit.compose.textfield

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import com.skyvo.mobile.core.uikit.compose.textfield.transformation.CreditCardNumberTransformation

@Composable
fun AppCardNumberTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String? = null,
    maxLength: Int = 16,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    showError: Boolean = false,
    errorText: String? = null,
    labelText: String? = null,
    onImeAction: () -> Unit = {},
    imeAction: ImeAction = ImeAction.Done,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    focusRequester: FocusRequester = remember { FocusRequester() }
) {
    AppInputTextField(
        modifier = modifier,
        value = value,
        onValueChange = { text ->
            onValueChange(text.filter { it.isDigit() })
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
        visualTransformation = CreditCardNumberTransformation(),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number,
            imeAction = imeAction,
            capitalization = KeyboardCapitalization.None,
            autoCorrectEnabled = false
        ),
        onImeAction = onImeAction,
        interactionSource = interactionSource,
        focusRequester = focusRequester,
        leftIcon = null
    )
}