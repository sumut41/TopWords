package com.skyvo.mobile.top.words.feature.books

import com.skyvo.mobile.core.base.manager.AppBook
import com.skyvo.mobile.core.base.viewmodel.UIState

data class BooksDashboardUIState(
    val showBookList: List<AppBook>? = emptyList(),
) : UIState