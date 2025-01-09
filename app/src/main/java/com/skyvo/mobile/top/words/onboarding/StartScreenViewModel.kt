package com.skyvo.mobile.top.words.onboarding

import com.skyvo.mobile.core.base.viewmodel.BaseComposeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StartScreenViewModel @Inject constructor() : BaseComposeViewModel<StartScreenUIState>() {
    override fun setInitialState(): StartScreenUIState {
        return StartScreenUIState()
    }
} 