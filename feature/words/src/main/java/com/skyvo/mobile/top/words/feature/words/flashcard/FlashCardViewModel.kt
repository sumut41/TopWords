package com.skyvo.mobile.top.words.feature.words.flashcard

import com.skyvo.mobile.core.base.manager.FiDataManager
import com.skyvo.mobile.core.base.viewmodel.BaseComposeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FlashCardViewModel @Inject constructor(
    private val dataManager: FiDataManager
): BaseComposeViewModel<FlashCardUIState>() {

    override fun setInitialState(): FlashCardUIState {
        return FlashCardUIState()
    }
}