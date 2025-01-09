package com.skyvo.mobile.core.uikit.compose.spinner

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.skyvo.mobile.core.uikit.compose.bottomsheet.AppAccountSpinnerBottomSheet
import com.skyvo.mobile.core.uikit.compose.picker.AppBasePickerComponent
import com.skyvo.mobile.core.uikit.compose.widget.AppAccountPickerWidget
import com.skyvo.mobile.core.uikit.compose.widget.AppAccountWidgetData

@Composable
fun AppAccountPicker(
    modifier: Modifier = Modifier,
    accountList: List<AppAccountWidgetData> = emptyList(),
    selectedPosition: Int = -1,
    onShowListener: ((Boolean) -> Unit)? = null,
    onItemClicked: (AppAccountWidgetData, Int) -> Unit
) {
    var selectedItem: AppAccountWidgetData? by remember { mutableStateOf(null) }
    var showBottomSheet by remember { mutableStateOf(false) }

    LaunchedEffect(selectedPosition) {
        selectedItem = if (selectedPosition > -1 && selectedPosition < accountList.count()) {
            accountList[selectedPosition]
        } else {
            null
        }
    }

    AppBasePickerComponent(modifier = modifier,
        isVisibleArrowRightIcon = accountList.size > 1,
        onClick = {
            showBottomSheet = true
            onShowListener?.invoke(true)
        }) {
        Column {
            AppAccountPickerWidget(accountPickerWidgetData = selectedItem)
        }
    }

    if (showBottomSheet) {
        AppAccountSpinnerBottomSheet(
            accountList = accountList,
            selectedPosition = selectedPosition,
            onItemClicked = { account, position ->
                selectedItem = account
                onItemClicked(account, position)
                showBottomSheet = false
                onShowListener?.invoke(false)
            },
            onDismiss = {
                showBottomSheet = false
                onShowListener?.invoke(false)
            }
        )
    }
}

// bunu kullanarak spinner yapıları kurabiliyoruz.