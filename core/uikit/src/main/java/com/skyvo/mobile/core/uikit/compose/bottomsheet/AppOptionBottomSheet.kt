package com.skyvo.mobile.core.uikit.compose.bottomsheet

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.skyvo.mobile.core.uikit.compose.checkbox.AppCheckBox
import com.skyvo.mobile.core.uikit.compose.layout.AppDivider
import com.skyvo.mobile.core.uikit.compose.layout.AppSpacer
import com.skyvo.mobile.core.uikit.compose.text.AppText
import com.skyvo.mobile.core.uikit.theme.AppDimension
import com.skyvo.mobile.core.uikit.theme.AppTypography

@Composable
fun AppOptionBottomSheet(
    title: String,
    options: List<String>,
    selectedIndex: Int = 0,
    onDismiss: () -> Unit,
    onOptionSelectIndex: (Int) -> Unit,
) {
    AppBottomSheet(
        title = title,
        onDismiss = onDismiss,
        isWithTopPadding = false
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = AppDimension.default.dp16)
        ) {
            itemsIndexed(options) { index, option ->
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = AppDimension.default.dp16,
                                top = AppDimension.default.dp16,
                                bottom = AppDimension.default.dp16,
                                end = AppDimension.default.dp8
                            )
                            .clickable {
                                onOptionSelectIndex.invoke(index)
                                onDismiss.invoke()
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AppText(
                            modifier = Modifier
                                .weight(9f),
                            text = option,
                            style = AppTypography.default.bodyPrimary
                        )

                        AppCheckBox(
                            modifier = Modifier
                                .weight(1f),
                            isChecked = index == selectedIndex
                        ) {
                            onOptionSelectIndex.invoke(index)
                            onDismiss.invoke()
                        }
                    }

                    AppDivider()
                }
            }

            item {
                AppSpacer(height = AppDimension.default.dp16)
            }
        }
    }
} 