package com.skyvo.mobile.core.uikit.compose.bottomsheet

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.skyvo.mobile.core.uikit.compose.layout.AppRowLinearMenu
import com.skyvo.mobile.core.uikit.theme.LocalAppColor
import com.skyvo.mobile.core.uikit.compose.bottomsheet.model.AppBottomSheetMenuItemModel

@Composable
fun AppMenuListBottomSheet(
    title: String? = null,
    onDismiss: () -> Unit,
    menuList: List<AppBottomSheetMenuItemModel>,
    onClickMenu: (AppBottomSheetMenuItemModel) -> Unit
) {
    AppBottomSheet(
        skipPartiallyExpanded = true,
        onDismiss = onDismiss,
        title = title,
        isWithTopPadding = false
    ) {
        // Burada BottomSheet Açılınca Göstermek İstediğimiz Tasarımı yapıyoruz.
        LazyColumn {
            items(menuList) {
                AppRowLinearMenu(
                    modifier = Modifier,
                    title = it.title.orEmpty(),
                    isNewTagVisible = it.isNew,
                    textColor = LocalAppColor.current.colorTextSubtler,
                    background = LocalAppColor.current.colorSurfaceBase
                ) {
                    onClickMenu.invoke(it)
                }
            }
        }
    }
}