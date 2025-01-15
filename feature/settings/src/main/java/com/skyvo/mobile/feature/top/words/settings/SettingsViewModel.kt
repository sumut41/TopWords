package com.skyvo.mobile.feature.top.words.settings

import com.skyvo.mobile.core.base.viewmodel.BaseComposeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor() : BaseComposeViewModel<SettingsUIState>() {
    override fun setInitialState(): SettingsUIState {
        return SettingsUIState()
    }
}