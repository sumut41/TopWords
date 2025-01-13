package com.skyvo.mobile.top.words.language

import com.skyvo.mobile.core.base.manager.UserManager
import com.skyvo.mobile.core.base.viewmodel.BaseComposeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChooseLanguageViewModel @Inject constructor(
    private val userManager: UserManager
): BaseComposeViewModel<ChooseLanguageUIState>() {

    override fun setInitialState(): ChooseLanguageUIState {
        return ChooseLanguageUIState()
    }

    fun updateSelectLanguage(code: String?) {
        setState {
            copy(
                selectLanguageCode = code,
                enableButton = code.isNullOrEmpty().not()
            )
        }
    }
}