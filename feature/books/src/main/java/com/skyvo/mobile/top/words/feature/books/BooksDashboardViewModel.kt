package com.skyvo.mobile.top.words.feature.books

import com.skyvo.mobile.core.base.viewmodel.BaseComposeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BooksDashboardViewModel @Inject constructor() : BaseComposeViewModel<BooksDashboardUIState>() {

    override fun setInitialState(): BooksDashboardUIState {
        return BooksDashboardUIState()
    }
}