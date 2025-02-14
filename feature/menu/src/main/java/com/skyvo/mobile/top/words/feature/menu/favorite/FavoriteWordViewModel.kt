package com.skyvo.mobile.top.words.feature.menu.favorite

import androidx.lifecycle.viewModelScope
import com.skyvo.mobile.core.base.manager.UserManager
import com.skyvo.mobile.core.base.viewmodel.BaseComposeViewModel
import com.skyvo.mobile.core.database.word.WordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteWordViewModel @Inject constructor(
    private val userManager: UserManager,
    private val wordRepository: WordRepository
): BaseComposeViewModel<FavoriteWordUIState>() {

    override fun setInitialState(): FavoriteWordUIState {
        return FavoriteWordUIState()
    }

    init {
        setState {
            copy(
                learnLanguageCode = userManager.learnLanguage?.code.orEmpty()
            )
        }
        getFavoriteWordList()
    }

    private fun getFavoriteWordList() {
        viewModelScope.launch {
            wordRepository.getFavoriteWordList(
                userManager.learnLanguage?.code.orEmpty()
            ).collect {
                setState {
                    copy(
                        items = it
                    )
                }
            }
        }
    }
}