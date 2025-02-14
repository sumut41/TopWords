package com.skyvo.mobile.top.words.feature.words.topword

import com.skyvo.mobile.core.base.viewmodel.UIState
import com.skyvo.mobile.core.database.word.WordEntity

data class TopWordListUIState (
    val level: String = "",
    val wordList: List<WordEntity>? = null,
    val learnLanguageCode: String? = null
): UIState