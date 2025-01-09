package com.skyvo.mobile.core.base.viewmodel.error

data class GenericErrorModel(
    val type: Int = 0,
    val title: String? = null,
    val message: String? = null,
    val htmlText: String? = null,
    val buttonActionType: Int = 0
)