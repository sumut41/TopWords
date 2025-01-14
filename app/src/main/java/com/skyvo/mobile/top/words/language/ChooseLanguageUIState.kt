package com.skyvo.mobile.top.words.language

import com.skyvo.mobile.core.base.manager.Language
import com.skyvo.mobile.core.base.viewmodel.UIState

data class ChooseLanguageUIState (
    val selectLanguage: Language? = null,
    val enableButton: Boolean = false,
    val languages: List<Language> = emptyList()
): UIState