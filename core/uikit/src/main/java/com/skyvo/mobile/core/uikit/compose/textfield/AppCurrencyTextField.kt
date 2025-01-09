package com.skyvo.mobile.core.uikit.compose.textfield

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.core.text.isDigitsOnly
import com.skyvo.mobile.core.uikit.util.UI_EMPTY
import com.skyvo.mobile.core.uikit.util.UI_ZERO
import com.skyvo.mobile.core.uikit.util.addMaskFractionalPart
import com.skyvo.mobile.core.uikit.util.addMaskInPart
import com.skyvo.mobile.core.uikit.util.addPrefix
import com.skyvo.mobile.core.uikit.util.applyFractionPartChange
import com.skyvo.mobile.core.uikit.util.checkDecimalSeparatorDeleted
import com.skyvo.mobile.core.uikit.util.checkDecimalSeparatorSecondPressed
import com.skyvo.mobile.core.uikit.util.checkFractionPartChanged
import com.skyvo.mobile.core.uikit.util.isCursorAtEnd
import com.skyvo.mobile.core.uikit.util.nextCharacter
import com.skyvo.mobile.core.uikit.util.toCurrency
import com.skyvo.mobile.core.uikit.compose.textfield.currency.AppCurrencyForcedValue
import com.skyvo.mobile.core.uikit.compose.textfield.currency.AppCurrencyState
import com.skyvo.mobile.core.uikit.compose.textfield.transformation.CurrencyVisualTransformation
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale
import kotlin.math.absoluteValue

@Composable
fun AppCurrencyTextField(
    modifier: Modifier = Modifier,
    defaultValue: AppCurrencyForcedValue? = null,
    onValueChange: (Double?) -> Unit,
    currencyState: AppCurrencyState = AppCurrencyState(),
    hasPlaceholder: Boolean = true,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    placeholder: String? = null,
    errorText: String? = null,
    showError: Boolean = false,
    maxLength: Int = 13,
    imeAction: ImeAction = ImeAction.Done,
    onImeAction: () -> Unit = {},
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    focusRequester: FocusRequester = remember { FocusRequester() },
    rightIcon: @Composable (() -> Unit)? = null
) {

    val visualTransformation by remember {
        mutableStateOf(CurrencyVisualTransformation(currencyState))
    }

    var text: TextFieldValue? by remember { mutableStateOf(null) }

    var isOnValueChangePrevented: Boolean by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(currencyState) {
        visualTransformation.currencyState = currencyState
    }

    LaunchedEffect(defaultValue) {
        defaultValue?.value.toCurrencyText(currencyState).orEmpty().also {
            if (it != text?.text) {
                if (defaultValue?.isPreventOnValueChange == true) {
                    isOnValueChangePrevented = true
                }
                text = text?.copy(text = it) ?: TextFieldValue(it)
            }
        }
    }

    LaunchedEffect(text?.text) {
        if (isOnValueChangePrevented) {
            isOnValueChangePrevented = false
        } else {
            onValueChange(text?.text.toCurrency())
        }
    }

    AppBasicTextField(
        modifier = modifier
            .focusRequester(focusRequester),
        value = text ?: TextFieldValue(),
        onValueChange = { textFieldValue ->
            var isCursorMoveIgnore = false
            val previous = text
            val previousText = previous?.text
            val dotFixedTextFieldValue =
                textFieldValue.copy(text = textFieldValue.text.replace('.', ','))

            if (currencyState.fractionDigitCount == 0) {
                text = if (dotFixedTextFieldValue.text.isDigitsOnly()) {
                    dotFixedTextFieldValue.copy(dotFixedTextFieldValue.text.removePrefix(String.UI_ZERO))
                } else {
                    previous
                }
            } else if (dotFixedTextFieldValue.selection.length == 0) {
                dotFixedTextFieldValue.text.let {
                    (
                            if (it.isEmpty() || (it.length == 1 && !it.isDigitsOnly())) {
                                null
                            } else if (it.length.minus(previousText?.length ?: 0).absoluteValue > 1) {
                                isCursorMoveIgnore = true
                                null
                            } else if (it.checkDecimalSeparatorSecondPressed(
                                    currencyState.decimalSeparator,
                                    previousText.orEmpty()
                                )
                            ) {
                                isCursorMoveIgnore =
                                    dotFixedTextFieldValue.nextCharacter() != currencyState.decimalSeparator.toString()
                                previousText
                            } else if (it.checkDecimalSeparatorDeleted(
                                    currencyState.decimalSeparator,
                                    previousText.orEmpty()
                                )
                            ) {
                                previousText
                            } else if (it.checkFractionPartChanged(
                                    currencyState,
                                    previousText.orEmpty()
                                )
                            ) {
                                it.addMaskInPart(currencyState)
                                    .applyFractionPartChange(
                                        currencyState,
                                        dotFixedTextFieldValue.isCursorAtEnd(),
                                        previousText.orEmpty()
                                    )
                                    .addMaskFractionalPart(currencyState)
                            } else if (it.firstOrNull() != null && it.firstOrNull()
                                    ?.digitToIntOrNull() == null
                            ) {
                                it.addPrefix(String.UI_ZERO).addMaskFractionalPart(currencyState)
                            } else if (
                                !previousText.isNullOrEmpty() &&
                                it.startsWith(String.UI_ZERO)
                            ) {
                                isCursorMoveIgnore = true
                                it.removePrefix(String.UI_ZERO).addMaskInPart(currencyState)
                                    .addMaskFractionalPart(currencyState)
                            } else {
                                it.addMaskInPart(currencyState)
                                    .addMaskFractionalPart(currencyState)
                            }
                            )
                }.let { it ->
                    val currency = it.toCurrency() ?: -1.0
                    if (currency in 0.0..currencyState.maxValue) {
                        val cursorPosition =
                            (if (isCursorMoveIgnore) {
                                previous?.selection
                                    ?: dotFixedTextFieldValue.selection
                            } else {
                                dotFixedTextFieldValue.selection
                            }).let { textRange ->
                                if (currency < 1.0 && textRange.end == 0) {
                                    TextRange(1, 1)
                                } else {
                                    textRange
                                }
                            }

                        text = dotFixedTextFieldValue.copy(
                            text = it.orEmpty(),
                            composition = dotFixedTextFieldValue.composition,
                            selection = cursorPosition
                        )
                    }
                }
            }
        },
        enabled = enabled,
        readOnly = readOnly,
        singleLine = true,
        maxLines = 1,
        showError = showError,
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.None,
            keyboardType = KeyboardType.Decimal,
            imeAction = imeAction,
            autoCorrectEnabled = false
        ),
        visualTransformation = visualTransformation,
        onImeAction = onImeAction,
        interactionSource = interactionSource,
        focusRequester = focusRequester,
        labelText = placeholder,
        placeholder = if (hasPlaceholder && text?.text.isNullOrEmpty()) currencyState.placeHolder else String.UI_EMPTY,
        errorText = errorText,
        rightIcon = rightIcon,
        maxLength = maxLength
    )
}

fun Double?.toCurrencyText(
    state: AppCurrencyState
): String? = this?.toDecimalWithCommaForCurrency()
    ?.replace(',', state.decimalSeparator)
    ?.replace('.', state.decimalSeparator)
    ?.addMaskFractionalPart(state)
    ?.let {
        if (state.fractionDigitCount == 0) {
            it.substringBefore(state.decimalSeparator)
        } else {
            it
        }
    }

fun Double.toDecimalWithCommaForCurrency(
    isDecimalRequired: Boolean = true,
    showTrailingZero: Boolean = true,
    groupingSeparator: Char = '.',
    decimalSeparator: Char = ','
): String {
    val otherSymbols = DecimalFormatSymbols(Locale.getDefault())
    otherSymbols.decimalSeparator = decimalSeparator
    otherSymbols.groupingSeparator = groupingSeparator
    val df = DecimalFormat(
        "#$groupingSeparator####",
        otherSymbols
    )
    df.minimumFractionDigits = if (showTrailingZero) 2 else 0

    return if (isDecimalRequired) {
        df.format(this)
    } else {
        df.format(this).split(decimalSeparator).firstOrNull().orEmpty()
    }
}

fun Double?.toAppCurrencyForcedValue(isPreventOnValueChange: Boolean = false) =
    AppCurrencyForcedValue(value = this, isPreventOnValueChange = isPreventOnValueChange)