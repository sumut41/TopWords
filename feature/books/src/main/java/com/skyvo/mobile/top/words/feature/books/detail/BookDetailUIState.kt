package com.skyvo.mobile.top.words.feature.books.detail

import com.skyvo.mobile.core.base.viewmodel.UIState
import com.skyvo.mobile.core.uikit.compose.widget.KeyValue
import com.skyvo.mobile.top.words.feature.books.model.BooksItem

data class BookDetailUIState(
    val book: BooksItem? = null,
    val selectedWord: KeyValue? = null,
    val isOriginalText: Boolean = true
): UIState