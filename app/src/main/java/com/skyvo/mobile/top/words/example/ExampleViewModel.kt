package com.skyvo.mobile.top.words.example

import com.skyvo.mobile.core.base.viewmodel.BaseComposeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExampleViewModel @Inject constructor(): BaseComposeViewModel<ExampleUIState>() {
    override fun setInitialState(): ExampleUIState {
        return ExampleUIState()
    }

    init {
        updateMessage()
    }

    private fun updateMessage() {
        setState {
            copy(
                message = "Bu bir example page"
            )
        }
    }
}