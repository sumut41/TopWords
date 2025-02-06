package com.skyvo.mobile.top.words.feature.books.search

import com.skyvo.mobile.core.base.manager.AppBook
import com.skyvo.mobile.core.uikit.util.UI_EMPTY

data class BooksSearchUIState(
    val books: List<AppBook> = emptyList(),
    val searchText: String = String.UI_EMPTY,
)