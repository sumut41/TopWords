package com.skyvo.mobile.top.words.feature.books

import com.skyvo.mobile.core.base.viewmodel.UIState
import com.skyvo.mobile.top.words.feature.books.model.BooksItem

data class BooksDashboardUIState(
    val beginnerBookList: List<BooksItem>? = emptyList(),
    val intermediateBookList: List<BooksItem>? = emptyList(),
    val advancedBookList: List<BooksItem>? = emptyList(),
    val selectedTabIndex: Int = 0
) : UIState