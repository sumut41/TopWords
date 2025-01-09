package com.skyvo.mobile.core.uikit.compose.checkbox

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import com.skyvo.mobile.core.uikit.theme.AppDimension
import com.skyvo.mobile.core.uikit.theme.AppTypography

@Composable
fun AppCheckBoxGroup(
    options: List<CheckBoxGroupOption>,
    columnSize: Int = 2,
    singleSelection: Boolean = false,
    titleTextStyle: TextStyle = AppTypography.default.body,
    onSelectionChange: (List<CheckBoxGroupOption>) -> Unit
) {
    var selectedOptions by remember { mutableStateOf(emptyList<CheckBoxGroupOption>()) }

    Column {
        for(chunk in options.chunked(columnSize)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                for(option in chunk) {
                 if (option.isSelected) {
                     selectedOptions += option
                 }
                    AppCheckBox(
                        modifier = Modifier
                            .weight(1f)
                            .padding(bottom = AppDimension.default.dp16)
                            .align(Alignment.CenterVertically),
                        title = option.title,
                        titleTextStyle = titleTextStyle,
                        isChecked = selectedOptions.contains(option),
                        onCheckedChange = {
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
    }
}

data class CheckBoxGroupOption(
    val id: Int,
    var title: String,
    var isSelected: Boolean
)