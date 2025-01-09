package com.skyvo.mobile.core.uikit.compose.bottomsheet.model

data class AppBottomSheetMenuItemModel(
    val key: String? = null,
    val title: String? = null,
    val deeplink: String? = null,
    val order: Int? = 1,
    val navigationId: Int? = null,
    val isNew: Boolean = false,
    val status: Int = 0,
    val iconKey: String? = null,
)