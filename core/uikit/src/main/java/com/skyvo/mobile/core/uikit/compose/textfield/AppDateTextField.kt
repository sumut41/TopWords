package com.skyvo.mobile.core.uikit.compose.textfield

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import com.skyvo.mobile.core.uikit.theme.AppDimension
import com.skyvo.mobile.core.uikit.theme.AppTypography
import com.skyvo.mobile.core.uikit.theme.LocalAppColor
import com.skyvo.mobile.core.uikit.util.MASK_DATE_FORMAT
import com.skyvo.mobile.core.uikit.util.formatMask
import com.skyvo.mobile.core.uikit.compose.button.AppPrimarySmallButton
import com.skyvo.mobile.core.uikit.compose.button.AppSecondarySmallButton
import com.skyvo.mobile.core.uikit.compose.text.AppText
import com.skyvo.mobile.core.uikit.compose.textfield.transformation.DateTransformation
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDateTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    onFormatChange: ((String) -> Unit)? = null,
    onValueChange: ((String) -> Unit)? = null,
    currentDate: AppDateData? = null,
    maxDate: AppDateData? = null,
    minDate: AppDateData? = null,
    placeholder: String? = null,
    maxLength: Int = 8,
    readOnly: Boolean = true,
    showError: Boolean = false,
    errorText: String? = null,
    labelText: String? = null,
    imeAction: ImeAction = ImeAction.Done,
    onImeAction: () -> Unit = {},
    outputDateFormat: SimpleDateFormat = SimpleDateFormat("yyyyMMdd", Locale.ROOT),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    focusRequester: FocusRequester = remember { FocusRequester() }
) {
    val calendar = Calendar.getInstance()
    var minDateCalendar: Calendar? = null
    var maxDateCalendar: Calendar? = null

    currentDate?.let {
        calendar.set(Calendar.YEAR, it.year ?: calendar.get(Calendar.YEAR))
        calendar.set(Calendar.MONTH, it.month?.minus(1) ?: calendar.get(Calendar.MONTH))
        calendar.set(Calendar.DAY_OF_MONTH, it.day ?: calendar.get(Calendar.DAY_OF_MONTH))
    } ?: run {
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR))
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH))
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH))
    }

    minDate?.let {
        minDateCalendar = Calendar.getInstance().apply {
            timeInMillis = calendar.timeInMillis
            add(Calendar.DAY_OF_YEAR, it.day ?: 0)
        }
    }

    maxDate?.let {
        maxDateCalendar = Calendar.getInstance().apply {
            timeInMillis = calendar.timeInMillis
            add(Calendar.DAY_OF_YEAR, it.day ?: 0)
        }
    }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = calendar.timeInMillis,
        yearRange = currentDate?.year?.let {
            IntRange(start = it.minus(minDate?.year ?: 10), endInclusive = it + (maxDate?.year ?: 1))
        } ?: run {
            IntRange(start = calendar.get(Calendar.YEAR).minus(minDate?.year ?: 10), endInclusive = calendar.get(Calendar.YEAR) + (maxDate?.year ?: 1))
        }
    )

    val selectedDate = datePickerState.selectedDateMillis?.let {
        calendar.set(Calendar.DAY_OF_YEAR, currentDate?.day ?: calendar.get(Calendar.DAY_OF_YEAR))
        calendar.timeInMillis = it
        calendar.time
    }

    val openDialog = remember { mutableStateOf(false) }

    val formatValueDate = remember { mutableStateOf(value) }
    LaunchedEffect(value) {
        if (value.isEmpty()) {
            formatValueDate.value = value
        }
    }

    Box(modifier = modifier.clickable(readOnly) {
        openDialog.value = true
    }) {
        AppInputTextField(
            value = formatValueDate.value,
            onValueChange = { },
            placeholder = placeholder ?: labelText,
            maxLength = maxLength,
            enabled = readOnly.not(),
            readOnly = readOnly,
            singleLine = true,
            maxLines = 1,
            showError = showError,
            errorText = errorText,
            labelText = labelText ?: placeholder,
            visualTransformation = DateTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Phone,
                imeAction = imeAction,
                capitalization = KeyboardCapitalization.None
            ),
            onImeAction = onImeAction,
            interactionSource = interactionSource,
            focusRequester = focusRequester,
            leftIcon = null
        )
    }

    if (openDialog.value) {
        DatePickerDialog(
            shape = RoundedCornerShape(2),
            colors = DatePickerDefaults.colors(
                titleContentColor = LocalAppColor.current.colorTextMain,
                headlineContentColor = LocalAppColor.current.colorSurfaceBase,
                containerColor = LocalAppColor.current.colorSurfaceBase
            ),
            onDismissRequest = {
                openDialog.value = false
            },
            confirmButton = {
                AppPrimarySmallButton(
                modifier = Modifier.padding(
                    bottom = AppDimension.default.dp16,
                    end = AppDimension.default.dp16
                ),
                text = "Tamam"
            ) {
                    openDialog.value = false
                    selectedDate?.let {
                        val formatter = SimpleDateFormat("ddMMyyyy", Locale.ROOT)
                        formatValueDate.value = formatter.format(it)
                        if (onFormatChange != null) {
                            onFormatChange(formatter.format(it).formatMask(MASK_DATE_FORMAT))
                        }
                        if (onValueChange != null) {
                            onValueChange(outputDateFormat.format(it))
                        }
                    }
                }
            },
            dismissButton = {
                AppSecondarySmallButton(
                    text = "Vazgeç"
                ) {
                    openDialog.value = false
                }
            }
        ) {
           DatePicker(
               state = datePickerState,
               showModeToggle = false,
               title = {
                   AppText(
                       modifier = Modifier.padding(
                           horizontal = AppDimension.default.dp24
                       ),
                       text = labelText ?: "",
                       style = AppTypography.default.bodyExtraLargeSemiBold
                   )
               },
              /* Todo  DateFormatter yok!!!!
               dateFormatter = { dateInMillis ->
                   if (minDateCalendar != null && maxDateCalendar != null) {
                       dateInMillis in minDateCalendar?.timeInMillis!!..maxDateCalendar?.timeInMillis!!
                   } else {
                       true
                   }
               } */
           )
        }
    }
}

data class AppDateData(
    val year: Int? = null,
    val month: Int? = null,
    val day: Int? = null
)