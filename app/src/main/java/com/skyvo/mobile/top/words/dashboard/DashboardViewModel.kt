package com.skyvo.mobile.top.words.dashboard

import com.skyvo.mobile.core.base.manager.UserManager
import com.skyvo.mobile.core.base.viewmodel.BaseComposeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val userManager: UserManager
) : BaseComposeViewModel<DashboardUIState>() {
    
    override fun setInitialState(): DashboardUIState {
        return DashboardUIState()
    }

    init {
        setState {
            copy(
                nativeLanguage = userManager.nativeLanguage,
                learnLanguage = userManager.learnLanguage
            )
        }
    }
} 