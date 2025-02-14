package com.skyvo.mobile.top.words.feature.menu.appinfo

import com.skyvo.mobile.core.base.viewmodel.BaseComposeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AboutUsViewModel @Inject constructor(): BaseComposeViewModel<AboutUsUIState>() {
    override fun setInitialState(): AboutUsUIState {
        return AboutUsUIState()
    }
}