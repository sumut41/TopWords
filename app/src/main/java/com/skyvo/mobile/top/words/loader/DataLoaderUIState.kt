package com.skyvo.mobile.top.words.loader

import com.skyvo.mobile.core.base.viewmodel.UIState

data class DataLoaderUIState (
    val language: String? = null,
    val nativeLanguageCode: String? = null,
    val learnLanguageCode: String? = null
): UIState