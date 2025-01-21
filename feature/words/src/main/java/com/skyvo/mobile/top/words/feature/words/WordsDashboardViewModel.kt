package com.skyvo.mobile.top.words.feature.words

import com.skyvo.mobile.core.base.viewmodel.BaseComposeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WordsDashboardViewModel @Inject constructor() :
    BaseComposeViewModel<WordsDashboardUIState>() {

    override fun setInitialState(): WordsDashboardUIState {
        return WordsDashboardUIState()
    }

    fun updateTabIndex(index: Int) {
        setState {
            copy(
                selectedTabIndex = index
            )
        }
    }
}