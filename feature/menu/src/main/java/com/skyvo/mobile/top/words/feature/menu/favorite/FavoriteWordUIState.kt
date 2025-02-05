package com.skyvo.mobile.top.words.feature.menu.favorite

import com.skyvo.mobile.core.base.viewmodel.UIState
import com.skyvo.mobile.core.database.word.WordEntity

data class FavoriteWordUIState (
    val items: List<WordEntity>? = null
): UIState