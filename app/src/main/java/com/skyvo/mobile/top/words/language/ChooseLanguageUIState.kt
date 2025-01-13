package com.skyvo.mobile.top.words.language

import com.skyvo.mobile.core.base.viewmodel.UIState

data class ChooseLanguageUIState (
    val selectLanguageCode: String? = null,
    val enableButton: Boolean = false
): UIState