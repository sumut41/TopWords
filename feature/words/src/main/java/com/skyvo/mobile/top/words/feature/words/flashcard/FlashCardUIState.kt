package com.skyvo.mobile.top.words.feature.words.flashcard

import com.skyvo.mobile.core.base.viewmodel.UIState
import com.skyvo.mobile.core.uikit.compose.card.FlashcardItem

data class FlashCardUIState(
    val items: List<FlashcardItem>? = null
) : UIState