package com.skyvo.mobile.top.words.feature.books.detail

import com.skyvo.mobile.core.base.manager.AppBook
import com.skyvo.mobile.core.base.viewmodel.UIState
import com.skyvo.mobile.core.uikit.compose.widget.KeyValue

data class BookDetailUIState(
    val book: AppBook? = null,
    val selectedWord: KeyValue? = null,
    val isOriginalText: Boolean = true,
    val sentences: List<String>? = emptyList(),
    val translations: List<String>? = emptyList(),
    val selectedSentence: Pair<String, String?> = Pair("", null),
): UIState