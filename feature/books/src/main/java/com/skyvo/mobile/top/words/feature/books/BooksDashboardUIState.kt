package com.skyvo.mobile.top.words.feature.books

import com.skyvo.mobile.core.base.manager.AppBook
import com.skyvo.mobile.core.base.viewmodel.UIState
import com.skyvo.mobile.core.uikit.compose.widget.Book

data class BooksDashboardUIState(
    val showBookList: List<AppBook>? = emptyList(),
    val selectFilterIndex: Int = 0
) : UIState