package com.skyvo.mobile.top.words.language

import com.skyvo.mobile.core.base.manager.Language
import com.skyvo.mobile.core.base.viewmodel.UIState

data class NaturalLanguageUIState(
    val languages: List<Language> = emptyList(),
    val selectedLanguage: Language? = null
) : UIState