package com.skyvo.mobile.top.words.feature.menu.learned

import androidx.lifecycle.viewModelScope
import com.skyvo.mobile.core.base.manager.UserManager
import com.skyvo.mobile.core.base.viewmodel.BaseComposeViewModel
import com.skyvo.mobile.core.database.word.WordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LearnedWordViewModel @Inject constructor(
    private val userManager: UserManager,
    private val wordRepository: WordRepository
) : BaseComposeViewModel<LearnedWordUIState>() {

    override fun setInitialState(): LearnedWordUIState {
        return LearnedWordUIState()
    }

    init {
        getLearnedWordList()
    }

    private fun getLearnedWordList() {
        viewModelScope.launch {
            wordRepository.getLearnedWordList(
                userManager.learnLanguage?.code.orEmpty()
            ).collect {
                setState {
                    copy(
                        learnedWords = it,
                        learnLanguageCode = userManager.learnLanguage?.code,
                    )
                }
            }
        }
    }
}