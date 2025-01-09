package com.skyvo.mobile.core.uikit.compose.bottomsheet

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.skyvo.mobile.core.uikit.compose.widget.AppAccountPickerWidget
import com.skyvo.mobile.core.uikit.compose.widget.AppAccountWidgetData
import com.skyvo.mobile.core.uikit.theme.AppDimension
import com.skyvo.mobile.core.uikit.theme.LocalAppColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AppAccountSpinnerBottomSheet(
    accountList: List<AppAccountWidgetData> = emptyList(),
    selectedPosition: Int = -1,
    onItemClicked: (AppAccountWidgetData, Int) -> Unit,
    onDismiss: () -> Unit
) {
    var selectedPositionRemember by remember { mutableIntStateOf(selectedPosition) }
    val scope = rememberCoroutineScope()

    AppBottomSheet(
        onDismiss = onDismiss,
        isWithTopPadding = false,
        skipPartiallyExpanded = true
    ) {
        Column {
            LazyColumn {
                itemsIndexed(accountList) { index, item ->
                    AppAccountSpinnerRow(
                        account = item,
                        isSelected = index == selectedPositionRemember
                    ) {
                        selectedPositionRemember = index
                        scope.launch {
                            delay(150L)
                            onItemClicked(item, selectedPositionRemember)
                            onDismiss()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AppAccountSpinnerRow(
    account: AppAccountWidgetData? = null,
    isSelected: Boolean = false,
    onItemClicked: (AppAccountWidgetData) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(AppDimension.default.dp12)
            .clip(RoundedCornerShape(AppDimension.default.dp4))
            .border(
                width = AppDimension.default.dp1,
                color = if (isSelected) {
                    LocalAppColor.current.primary
                } else {
                    LocalAppColor.current.colorBorderInputsDefault
                },
                shape = RoundedCornerShape(AppDimension.default.dp4)
            )
            .clickable {
                if (account != null) {
                    onItemClicked(account)
                }
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (account != null) {
            AppAccountPickerWidget(
                modifier = Modifier.padding(
                    horizontal = AppDimension.default.dp16,
                    vertical = AppDimension.default.dp20
                ),
                accountPickerWidgetData = account
            )
        }
    }
}

/*
bunlar bottomsheet açıldığında göstereceğimiz itemların içindeki propertyler.
onItemClicked: (AppAccountWidgetData, Int) -> Unit, buradaki int ile index dönüyor ,ilk kısım seçilen
itemi temsil ediyor. verileri vmde ilk çektiğimizde selectedposition ı eğer liste boş değilse 0 olarak seçiyoruz.

AppAccountSpinnerRow -> BottomSheet açılınca gösterilecek itemler.
AppAccountPickerWidget -> Her bir itemın tasarımı

bunları oluşturduktan sonra bu bottomsheeti kullanarak bir listeyi göstermek istersek bunun bir de
spinner ını oluşturuyoruz ve işlem bitiyor.

1- bottomsheet itemlarını yap - widget
2- bottomsheeti oluştur
3- spinner a geç
 */