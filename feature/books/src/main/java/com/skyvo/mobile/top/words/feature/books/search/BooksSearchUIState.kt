package com.skyvo.mobile.top.words.feature.books.search

import com.skyvo.mobile.core.uikit.util.UI_EMPTY
import com.skyvo.mobile.top.words.feature.books.model.BooksItem

data class BooksSearchUIState(
    val bookType: Int = 0,
    val books: List<BooksItem> = emptyList(),
    val searchText: String = String.UI_EMPTY,
)