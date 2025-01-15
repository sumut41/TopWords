package com.skyvo.mobile.top.words.language

import androidx.lifecycle.viewModelScope
import com.skyvo.mobile.core.base.manager.Language
import com.skyvo.mobile.core.base.manager.UserManager
import com.skyvo.mobile.core.base.navigation.NavDeeplinkDestination
import com.skyvo.mobile.core.base.navigation.navigate
import com.skyvo.mobile.core.base.viewmodel.BaseComposeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChooseLanguageViewModel @Inject constructor(
    private val userManager: UserManager
): BaseComposeViewModel<ChooseLanguageUIState>() {

    override fun setInitialState(): ChooseLanguageUIState {
        return ChooseLanguageUIState()
    }

    fun setLanguageList(list: ArrayList<Language>) {
        setState {
            copy(
                languages = list
            )
        }
    }

    fun updateSelectLanguage(language: Language?) {
        setState {
            copy(
                selectLanguage = language,
                enableButton = language != null
            )
        }
    }

    fun next() {
        state.value.selectLanguage?.let { language ->
            userManager.learnLanguage = language
            viewModelScope.launch {
                delay(100)
                navigate(NavDeeplinkDestination.WordsDashboard)
            }
        }
    }
}