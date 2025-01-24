package com.skyvo.mobile.top.words.feature.words

import com.skyvo.mobile.core.base.manager.UserManager
import com.skyvo.mobile.core.base.viewmodel.BaseComposeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WordsDashboardViewModel @Inject constructor(
    private val userManager: UserManager
) :
    BaseComposeViewModel<WordsDashboardUIState>() {

    override fun setInitialState(): WordsDashboardUIState {
        return WordsDashboardUIState()
    }

    init {
        setState {
            copy(
                learnLanguage = userManager.learnLanguage
            )
        }
    }

    fun updateTabIndex(index: Int) {
        setState {
            copy(
                selectedTabIndex = index
            )
        }
    }
}