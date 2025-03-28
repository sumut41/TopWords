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

    fun updateWord(id: Long, isFavorite: Boolean) {
        viewModelScope.launch {
            wordRepository.markWordAsFavorite(
                isFavorite = isFavorite.not(),
                id = id,
                languageCode = userManager.learnLanguage?.code
            )
        }

        setState {
            copy(
                learnedWords = learnedWords?.map { word ->
                    if (word.id == id) word.copy(isFavorite = isFavorite.not()) else word
                }
            )
        }
    }
}