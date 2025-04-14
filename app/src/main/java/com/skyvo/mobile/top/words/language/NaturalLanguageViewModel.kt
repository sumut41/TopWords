package com.skyvo.mobile.top.words.language

import com.skyvo.mobile.core.base.manager.Language
import com.skyvo.mobile.core.base.manager.UserManager
import com.skyvo.mobile.core.base.viewmodel.BaseComposeViewModel
import com.skyvo.mobile.core.uikit.R
import dagger.hilt.android.lifecycle.HiltViewModel
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
        state.value.selectedLanguage?.let { language ->
            userManager.nativeLanguage = language
            userManager.learnLanguage = Language(
                code = "en",
                name = "English",
                icon = R.drawable.ic_flag_en
            )
            navigate(NaturalLanguageFragmentDirections.actionNaturalLanguageFragmentToStartScreenFragment())
        }
    }
} 