package com.skyvo.mobile.core.uikit.compose.radiobutton

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import com.skyvo.mobile.core.uikit.compose.layout.AppSpacer
import com.skyvo.mobile.core.uikit.theme.AppDimension

@Composable
fun AppRadioButtonGroup(
    modifier: Modifier = Modifier,
    options: List<RadioButtonGroupOption>,
    columnSize: Int = 2,
    selectedOption: RadioButtonGroupOption? = RadioButtonGroupOption(0),
    singleSelection: Boolean = false,
    itemSpacing: Dp = AppDimension.default.dp0,
    onSelectionChange: (List<RadioButtonGroupOption>) -> Unit
) {
    var selectedOptions by remember { mutableStateOf(emptyList<RadioButtonGroupOption>()) }

    LaunchedEffect(selectedOptions) {
        selectedOptions = if (selectedOption != null) {
            listOf(selectedOption)
        } else {
            emptyList()
        }
    }
    Column(
        modifier = modifier
    ) {
        for (chunk in options.chunked(columnSize)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = AppDimension.default.dp2)
            ) {
                for (option in chunk) {
                    AppRadioButton(
                        modifier = Modifier.weight(1f),
                        text = if (option.titleIntRes != null) {
                            stringResource(option.titleIntRes!!)
                        } else if (!option.title.isNullOrEmpty()) {
                            option.title.orEmpty()
                        } else {
                            ""
                        },
                        isSelected = if (option.isSelected) {
                            option.isSelected
                        } else {
                            selectedOptions.contains(option)
                        },
                        onClick = {
                            if (singleSelection) {
                                selectedOptions = listOf(option)
                                onSelectionChange(selectedOptions)
                            } else {
                                selectedOptions = if (selectedOptions.contains(option)) {
                                    selectedOptions - option
                                } else {
                                    selectedOptions + option
                                }
                                onSelectionChange(selectedOptions)
                            }
                        }
                    )
                }
            }
        }
        AppSpacer(height = itemSpacing)
    }
}

data class RadioButtonGroupOption(
    val id: Int,
    var titleIntRes: Int? = null,
    var title: String? = null,
    var isSelected: Boolean = false
)