package com.skyvo.mobile.top.words.language

import androidx.lifecycle.viewModelScope
import com.skyvo.mobile.core.base.manager.Language
import com.skyvo.mobile.core.base.manager.UserManager
import com.skyvo.mobile.core.base.navigation.navigate
import com.skyvo.mobile.core.base.viewmodel.BaseComposeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NaturalLanguageViewModel @Inject constructor(
    private val userManager: UserManager
) : BaseComposeViewModel<NaturalLanguageUIState>() {

    override fun setInitialState(): NaturalLanguageUIState {
        return NaturalLanguageUIState()
    }

    fun setLanguageList(list: ArrayList<Language>) {
        setState {
            copy(
                languages = list
            )
        }
    }

    fun onLanguageSelected(language: Language) {
        setState {
            copy(selectedLanguage = language)
        }
    }


    fun next() {
        userManager.learnLanguage = state.value.selectedLanguage
        viewModelScope.launch {
            delay(100)
            navigate(NaturalLanguageFragmentDirections.actionNaturalLanguageFragmentToChooseLanguageFragment())
        }
    }
} 