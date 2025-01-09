package com.skyvo.mobile.core.uikit.compose.widget

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.skyvo.mobile.core.uikit.compose.text.AppText

@Composable
fun AppAccountPickerWidget(
    modifier: Modifier = Modifier,
    accountPickerWidgetData: AppAccountWidgetData? = null
) {
    // örnek olarak her bir item alt alta
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        AppText(
            text = accountPickerWidgetData?.title.orEmpty()
        )
        AppText(
            text = accountPickerWidgetData?.iban.orEmpty()
        )
        AppText(
            text = accountPickerWidgetData?.imageUrl.orEmpty()
        )
        AppText(
            text = accountPickerWidgetData?.balance.orEmpty()
        )
    }
}

data class AppAccountWidgetData(
    val title: String? = null,
    val iban: String? = null,
    val balance: String? = null,
    val imageUrl: String? = null,
)