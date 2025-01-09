package com.skyvo.mobile.core.uikit.compose.button

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.skyvo.mobile.core.uikit.theme.AppDimension
import com.skyvo.mobile.core.uikit.theme.LocalAppColor

@Composable
fun AppButtonGroup(
    modifier: Modifier = Modifier,
    options: List<ButtonGroupOption>,
    columnSize: Int = 2,
    isDeletableOption: Boolean = true,
    onSelectionChanged: (ButtonGroupOption) -> Unit
) {
    var selectedOption by remember { mutableStateOf(ButtonGroupOption(id = -1, titleIntRes = null)) }
    var optionsList = options

    LaunchedEffect(key1 = options) {
        selectedOption = options.filter { it.isSelected }.firstOrNull() ?: ButtonGroupOption(-1, null)
        optionsList = options
    }

    Column(
        modifier = modifier
    ) {
        for (chunk in optionsList.chunked(columnSize)) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                for (option in chunk) {
                    AppSecondarySmallButton(
                        modifier = Modifier
                            .heightIn(min = AppDimension.default.dp32)
                            .weight(1f)
                            .fillMaxWidth()
                            .padding(
                                end = AppDimension.default.dp4,
                                start = AppDimension.default.dp4,
                                bottom = AppDimension.default.dp16,
                            )
                            .border(
                                width = AppDimension.default.dp1,
                                color = if (selectedOption == option) {
                                    LocalAppColor.current.colorBorderSelected
                                } else {
                                    LocalAppColor.current.colorBorderInputsDefault
                                },
                                shape = RoundedCornerShape(AppDimension.default.dp4)
                            ),
                        text = if (option.titleIntRes != null) {
                            stringResource(id = option.titleIntRes!!)
                        } else if (option.title != null) {
                            option.title ?: ""
                        } else {
                            ""
                        },
                        containerColor = if (selectedOption == option) {
                            LocalAppColor.current.colorBackgroundSelected
                        } else {
                            LocalAppColor.current.colorBackgroundButtonSecondaryDefault
                        },
                        onClick = {
                            if (isDeletableOption) {
                                if (selectedOption == option) {
                                    selectedOption = ButtonGroupOption(-1, null)
                                    onSelectionChanged(selectedOption)
                                } else {
                                    selectedOption = option
                                    onSelectionChanged(selectedOption)
                                }
                            } else {
                                if (selectedOption == option) {
                                    onSelectionChanged(selectedOption)
                                } else {
                                    selectedOption = option
                                    onSelectionChanged(option)
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

data class ButtonGroupOption(
    val id: Int? = null,
    var titleIntRes: Int? = null,
    var title: String? = null,
    var isSelected: Boolean = false
)