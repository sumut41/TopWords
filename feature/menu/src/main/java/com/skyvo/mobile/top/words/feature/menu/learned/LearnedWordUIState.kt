package com.skyvo.mobile.top.words.feature.menu.learned

import com.skyvo.mobile.core.base.viewmodel.UIState
import com.skyvo.mobile.core.database.word.WordEntity

data class LearnedWordUIState(
    val learnedWords: List<WordEntity>? = null,
    val learnLanguageCode: String? = null
) : UIState