package com.skyvo.mobile.top.words.feature.words.topword

import android.os.Bundle
import androidx.lifecycle.viewModelScope
import com.skyvo.mobile.core.base.manager.UserManager
import com.skyvo.mobile.core.base.viewmodel.BaseComposeViewModel
import com.skyvo.mobile.core.database.word.WordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopWordListViewModel @Inject constructor(
    private val userManager: UserManager,
    private val wordRepository: WordRepository
) : BaseComposeViewModel<TopWordListUIState>() {

    override fun setInitialState(): TopWordListUIState {
        return TopWordListUIState()
    }

    override fun fetchExtras(extra: Bundle) {
        super.fetchExtras(extra)
        with(TopWordListFragmentArgs.fromBundle(extra)) {
            getLearnedWordList(level.orEmpty())
        }
    }

    private fun getLearnedWordList(level: String) {
        viewModelScope.launch {
            wordRepository.getLevelWordList(
                level = level,
                languageCode = userManager.learnLanguage?.code.orEmpty()
            ).collect {
                setState {
                    copy(
                        level = level,
                        wordList = it?.sortedBy { it.word?.trim()?.lowercase() },
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
                wordList = wordList?.map { word ->
                    if (word.id == id) word.copy(isFavorite = isFavorite.not()) else word
                }
            )
        }
    }
}